package org.marketcetera.messagehistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;

import org.marketcetera.core.MSymbol;
import org.marketcetera.quickfix.FIXMessageFactory;
import org.marketcetera.util.misc.ClassVersion;
import org.marketcetera.util.log.SLF4JLoggerProxy;
import org.marketcetera.trade.*;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.field.Account;
import quickfix.field.AvgPx;
import quickfix.field.CumQty;
import quickfix.field.LeavesQty;
import quickfix.field.MsgType;
import quickfix.field.OrderQty;
import quickfix.field.Side;
import quickfix.field.Symbol;
import ca.odell.glazedlists.AbstractEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

/* $License$ */

/**
 * LatestExecutionReportsFunction is a subclass of
 * LatestMessageFunction that filters for only incoming
 * execution report messages.
 *
 * @author anshul@marketcetera.com
 * @version $Id$
 * @since $Release$
 */
@ClassVersion("$Id$") //$NON-NLS-1$
public class AveragePriceReportList extends AbstractEventList<ReportHolder> implements ListEventListener<ReportHolder> {

	private HashMap<SymbolSide, Integer> averagePriceIndexes = new HashMap<SymbolSide, Integer>();
	private ArrayList<ReportHolder> averagePricesList = new ArrayList<ReportHolder>();

	private FIXMessageFactory messageFactory;

	public AveragePriceReportList(FIXMessageFactory messageFactory, EventList<ReportHolder> source) {
		super(source.getPublisher());
		this.messageFactory = messageFactory;
		source.addListEventListener(this);

		readWriteLock = source.getReadWriteLock();
	}

	public void listChanged(ListEvent<ReportHolder> listChanges) {
        // all of these changes to this list happen "atomically"
        updates.beginEvent();

        // handle reordering events
        if(!listChanges.isReordering()) {
            // for all changes, one index at a time
            while(listChanges.next()) {

                // get the current change info
                int changeType = listChanges.getType();

                EventList<ReportHolder> sourceList = listChanges.getSourceList();
            	// handle delete events
                if(changeType == ListEvent.DELETE || changeType == ListEvent.UPDATE) {
                	throw new UnsupportedOperationException();
                } else if(changeType == ListEvent.INSERT) {
	            	ReportHolder deltaReportHolder = sourceList.get(listChanges.getIndex());

	            	Integer averagePriceIndex = null;

	            	try {
		            	Message deltaMessage = deltaReportHolder.getMessage();
		            	ReportBase deltaReport = deltaReportHolder.getReport();
						String symbol = deltaMessage.getString(Symbol.FIELD);
						String side = deltaMessage.getString(Side.FIELD);
						SymbolSide symbolSide = new SymbolSide(new MSymbol(symbol), side);
						averagePriceIndex = averagePriceIndexes.get(symbolSide);

						if(averagePriceIndex != null) {
	                    	ReportHolder averagePriceReportHolder = averagePricesList.get(averagePriceIndex);
		                    Message averagePriceMessage = averagePriceReportHolder.getMessage();
		                    ExecutionReport averagePriceReport = (ExecutionReport) averagePriceReportHolder.getReport();

                            if (deltaReportHolder instanceof ReportHolder && deltaReport instanceof ExecutionReport) {
                                ExecutionReport execReport = (ExecutionReport) deltaReport;
                                BigDecimal lastQuantity = execReport.getLastQuantity();
                                if (lastQuantity == null)
                                	continue;
								if (lastQuantity.compareTo(BigDecimal.ZERO) > 0) {
                                    double existingCumQty = toDouble(averagePriceReport.getCumulativeQuantity());
                                    double existingAvgPx = toDouble(averagePriceReport.getAveragePrice());
                                    double newLastQty = toDouble(lastQuantity);
                                    double newLastPx = toDouble(execReport.getLastPrice());
                                    double newTotal = existingCumQty + newLastQty;
                                    if (newTotal != 0.0){
                                        double numerator = (existingCumQty * existingAvgPx)+(newLastQty * newLastPx);
                                        double newAvgPx = numerator / newTotal;
                                        averagePriceMessage.setDouble(AvgPx.FIELD, newAvgPx);
                                        averagePriceMessage.setDouble(CumQty.FIELD, newTotal);
                                    }
                                }
                                // The following block is for the PENDING NEW acks from ORS.
                                // TODO: Change this to look for custom ORS acks instead of PENDING_NEW
                                else if (deltaReport.getOrderStatus() == OrderStatus.PendingNew) {
                                    double orderQty = toDouble(averagePriceReport.getOrderQuantity());
                                    orderQty = orderQty + toDouble(execReport.getOrderQuantity());
                                    averagePriceMessage.setDouble(OrderQty.FIELD, orderQty);
                                }
                            }
                            updates.addUpdate(averagePriceIndex);
						} else {
							// TODO: Change this to look for custom ORS acks instead of PENDING_NEW
                            if (deltaReport instanceof ExecutionReport) {
                                ExecutionReport execReport = (ExecutionReport) deltaReport;
                                BigDecimal lastQuantity = execReport.getLastQuantity();
								if (deltaReport.getOrderStatus() == OrderStatus.PendingNew || (lastQuantity != null && lastQuantity.compareTo(BigDecimal.ZERO) > 0)) { 
                                    Message averagePriceMessage = messageFactory.createMessage(MsgType.EXECUTION_REPORT);
                                    averagePriceMessage.setField(deltaMessage.getField(new Side()));
                                    averagePriceMessage.setField(deltaMessage.getField(new Symbol()));
                                    // The following block is for the PENDING NEW acks from ORS.
                                    // TODO: Change this to look for custom ORS acks instead of PENDING_NEW
                                    if (deltaReport.getOrderStatus() == OrderStatus.PendingNew){
                                        averagePriceMessage.setField(new OrderQty(execReport.getOrderQuantity()));
                                    } else {
                                    	if (execReport.getLeavesQuantity() != null)
                                    		averagePriceMessage.setField(new LeavesQty(execReport.getLeavesQuantity()));
                                        averagePriceMessage.setField(new CumQty(lastQuantity));
                                        averagePriceMessage.setField(new AvgPx(execReport.getLastPrice()));
                                    }
                                    if (execReport.getAccount() != null)
                                    	averagePriceMessage.setField(new Account(execReport.getAccount()));

                                    try {
                                        averagePricesList.add(new ReportHolder(Factory.getInstance().createExecutionReport(averagePriceMessage, execReport.getDestinationID())));
                                        averagePriceIndex = averagePricesList.size()-1;
                                        averagePriceIndexes.put(symbolSide, averagePriceIndex);
                                        updates.addInsert(averagePriceIndex);
                                    } catch (MessageCreationException e) {
                                        SLF4JLoggerProxy.error(this, "unexpected error", e);  //$NON-NLS-1$
                                    }
                                }
                            }
                        }
						// if this value was not filtered out, it is now so add a change

	            	} catch (FieldNotFound fnf){
	            		// ignore...
	            	}
                }
            }
        }

        // commit the changes and notify listeners
        updates.commitEvent();
	}

	@Override
	public ReportHolder get(int index) {
		return averagePricesList.get(index);
	}

	@Override
	public int size() {
		return averagePricesList.size();
	}

    private static double toDouble(BigDecimal inValue) {
        return inValue == null
                ? 0.0
                : inValue.doubleValue();
    }

}