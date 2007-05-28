package org.marketcetera.photon.preferences;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.marketcetera.photon.PhotonPlugin;
import org.marketcetera.photon.ui.EventListContentProvider;
import org.marketcetera.photon.ui.IndexedTableViewer;
import org.marketcetera.quickfix.FIXDataDictionary;
import org.marketcetera.quickfix.FIXMessageUtil;

import ca.odell.glazedlists.BasicEventList;

public class FIXMessageFieldColumnChooserEditor extends FieldEditor {
		
	private FIXMessageDetailPreferenceParser parser;
	
	private FIXDataDictionary fixDictionary;

	private char orderType;
	
	private Table availableFieldsTable;

	private Table chosenFieldsTable;

	private IndexedTableViewer availableFieldsTableViewer;

	private IndexedTableViewer chosenFieldsTableViewer;

	private Composite addRemoveButtonBox;

	private Button addButton;

	private Button removeButton;

	private Button addAllButton;

	private Button removeAllButton;
	
	private Composite upDownButtonBox;

	private Button upButton;

	private Button downButton;

	private SelectionListener selectionListener;
	
	private Map<Character, FIXMessageFieldColumnChooserEditorPage> orderStatusToPageMap;	
	
	private BasicEventList<String> filteredAvailableEntries;
	
	private BasicEventList<String> filteredChosenEntries;
	
	private Map<String, Integer> fieldEntryToFieldIDMap;
	

	protected FIXMessageFieldColumnChooserEditor(String name, String labelText,
			Composite parent, char orderType) {
		init(name, labelText);
		this.fixDictionary = PhotonPlugin.getDefault().getFIXDataDictionary();
		this.orderType = orderType;
		this.parser = new FIXMessageDetailPreferenceParser();		
		this.orderStatusToPageMap = new HashMap<Character, FIXMessageFieldColumnChooserEditorPage>();
		loadFieldEntryToFieldIDMap();
		createControl(parent);
	}

	private void addPressed() {
		setPresentsDefaultValue(false);		
		BasicEventList<String> input = getNewInputObject(availableFieldsTable);
		if (input != null) {
			FIXMessageFieldColumnChooserEditorPage currPage = getCurrentPage();
			currPage.getAvailableFieldsList().removeAll(input);
			currPage.getChosenFieldsList().addAll(input);

			filteredAvailableEntries.removeAll(input);
			filteredChosenEntries.addAll(input);
			availableFieldsTableViewer.refresh(false);
			chosenFieldsTableViewer.refresh(false);	
			selectionChanged();
		}
	}

	protected void adjustForNumColumns(int numColumns) {
		Control control = getLabelControl();
		((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		((GridData) availableFieldsTable.getLayoutData()).horizontalSpan = numColumns - 1;
	}

	private void createAddRemoveButtons(Composite box) {
		addButton = createPushButton(box, "Add");//$NON-NLS-1$
		removeButton = createPushButton(box, "Remove");//$NON-NLS-1$
		addAllButton = createPushButton(box, "Add All");//$NON-NLS-1$
		removeAllButton = createPushButton(box, "Remove All");//$NON-NLS-1$
	}

	private void createUpDownButtons(Composite box) {
		upButton = createPushButton(box, "Up");//$NON-NLS-1$
		downButton = createPushButton(box, "Down");//$NON-NLS-1$
	}
	
	private Button createPushButton(Composite parent, String key) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(JFaceResources.getString(key));
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button,
				IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(getSelectionListener());
		return button;
	}

	private void createSelectionListener() {
		selectionListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Widget widget = event.widget;
				if (widget == addButton) {
					addPressed();
				} else if (widget == removeButton) {
					removePressed();
				} else if (widget == addAllButton) {
					addAllPressed();
				} else if (widget == removeAllButton) {
					removeAllPressed();
				} else if (widget == availableFieldsTable) {
					selectionChanged();
				} else if (widget == upButton) {
					upPressed();
				} else if (widget == downButton) {
					downPressed();
				}				
			}
		};
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);

		availableFieldsTable = createAvailableFieldsTable(parent);

		addRemoveButtonBox = getAddRemoveButtonBoxControl(parent);
		gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		gd.horizontalSpan = 1;
		addRemoveButtonBox.setLayoutData(gd);

		chosenFieldsTable = createChosenFieldsTable(parent);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalSpan = 1;
		gd.grabExcessHorizontalSpace = true;
		availableFieldsTable.setLayoutData(gd);
		chosenFieldsTable.setLayoutData(gd);

		availableFieldsTableViewer = createTableViewer(availableFieldsTable);
		chosenFieldsTableViewer = createTableViewer(chosenFieldsTable);
		
		upDownButtonBox = getUpDownButtonBoxControl(parent);
		gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		gd.horizontalSpan = 1;
		upDownButtonBox.setLayoutData(gd);
	}

	protected void doLoad() {
		doLoadDefault();
	}
	
	protected void doLoadDefault() {
		List<Integer> savedIntFields = parser.getFieldsToShow(orderType);
		FIXMessageFieldColumnChooserEditorPage currPage;
		boolean loadedBefore = hasBeenLoaded();
		if (loadedBefore) {
			currPage = loadPageFromMemory();
		} else {
			currPage = loadPageFromPreference();  
		}

		if (chosenFieldsTable != null) {
			loadChosenFieldsTable(loadedBefore, currPage, savedIntFields);
		}		
		if (availableFieldsTable != null) {
			loadDefaultAvailableFieldsTable(loadedBefore, currPage, savedIntFields);				
		}
		resetFilter();
	}
	
	private FIXMessageFieldColumnChooserEditorPage loadPageFromMemory() {
		return orderStatusToPageMap.get(orderType);
	}
	
	private FIXMessageFieldColumnChooserEditorPage loadPageFromPreference() {
		FIXMessageFieldColumnChooserEditorPage currPage = new FIXMessageFieldColumnChooserEditorPage(orderType);
		orderStatusToPageMap.put(orderType, currPage);
		return currPage;
	}

	private FIXMessageFieldColumnChooserEditorPage getCurrentPage() {
		return loadPageFromMemory();
	}
	
	private boolean hasBeenLoaded() {
		return orderStatusToPageMap.containsKey(orderType);
	}

	private void loadDefaultAvailableFieldsTable(boolean loadedBefore,
			FIXMessageFieldColumnChooserEditorPage currPage,
			List<Integer> savedIntFields) {
		availableFieldsTable.removeAll();
		BasicEventList<String> currPageAvailableFieldsEntries = new BasicEventList<String>();
		if (loadedBefore) {
			currPageAvailableFieldsEntries = currPage.getAvailableFieldsList();
		} else {
			Set<String> fieldEntryStrings = fieldEntryToFieldIDMap.keySet();
			for (String fieldEntry : fieldEntryStrings) {
				int fieldID = fieldEntryToFieldIDMap.get(fieldEntry);
				if (!savedIntFields.contains(fieldID)) {
					currPageAvailableFieldsEntries.add(fieldEntry);
				}
			}
		}
		currPage.setAvailableFieldsList(currPageAvailableFieldsEntries);
	}
	
	private void loadFieldEntryToFieldIDMap() {
		fieldEntryToFieldIDMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < FIXMessageUtil.getMaxFIXFields(); i++) {
			if (FIXMessageUtil.isValidField(i)) {
				String fieldName = fixDictionary.getHumanFieldName(i);
				String fieldEntry = fieldName + " (" + i + ")";
				fieldEntryToFieldIDMap.put(fieldEntry, i);
			}
		}
	}

	private void loadChosenFieldsTable(boolean loadedBefore,
			FIXMessageFieldColumnChooserEditorPage currPage,
			List<Integer> savedIntFields) {
		chosenFieldsTable.removeAll();
		BasicEventList<String> currPageChosenFieldsEntries = new BasicEventList<String>();

		if (loadedBefore) {
			currPageChosenFieldsEntries = currPage.getChosenFieldsList();
		} else {
			currPageChosenFieldsEntries = getChosenFields(savedIntFields);
			currPage.setChosenFieldsList(currPageChosenFieldsEntries);
		}
	}
	
	private BasicEventList<String> getChosenFields(List<Integer> savedIntFields) {
		BasicEventList<String> fieldsList = new BasicEventList<String>();			
		for (int intField : savedIntFields) {
			String fieldName = fixDictionary.getHumanFieldName(intField);
			fieldsList.add(fieldName + " (" + intField + ")");
		}
		return fieldsList;
	}
	
	private FIXMessageFieldColumnChooserEditorPage getCurrentChooserEditorPage() {
		if (!hasBeenLoaded()) {
			return null;
		}
		FIXMessageFieldColumnChooserEditorPage currentPage = loadPageFromMemory();
		return currentPage;
	}
	
	public void resetFilter() {
		FIXMessageFieldColumnChooserEditorPage currentPage = getCurrentChooserEditorPage();
		resetAvailableFilter(currentPage);
		resetChosenFilter(currentPage);
	}
	
	private void resetAvailableFilter(FIXMessageFieldColumnChooserEditorPage currentPage) {
		filteredAvailableEntries = new BasicEventList<String>();
		if(currentPage != null) {
			filteredAvailableEntries.addAll(currentPage.getAvailableFieldsList());
		}
		availableFieldsTableViewer.setInput(filteredAvailableEntries);
	}
	
	private void resetChosenFilter(FIXMessageFieldColumnChooserEditorPage currentPage) {
		filteredChosenEntries = new BasicEventList<String>();
		if(currentPage != null) {
			filteredChosenEntries.addAll(currentPage.getChosenFieldsList());
		}
		chosenFieldsTableViewer.setInput(filteredChosenEntries);
	}
	
	public void applyFilter(String filterText) {
		FIXMessageFieldColumnChooserEditorPage currentPage = getCurrentChooserEditorPage();
		if(currentPage == null) {
			return;
		}
		applyFilter(filterText, currentPage.getChosenFieldsList(), filteredChosenEntries);
		applyFilter(filterText, currentPage.getAvailableFieldsList(), filteredAvailableEntries);
	}
	
	private void applyFilter(String filterText,
			BasicEventList<String> allPossibleEntries,
			BasicEventList<String> currentFilteredEntries) {
		if(filterText == null) { 
			filterText = "";
		}
		for (String possibleEntry : allPossibleEntries) {
			if (isFilterMatch(filterText, possibleEntry)) {
				if (!currentFilteredEntries.contains(possibleEntry)) {
					currentFilteredEntries.add(possibleEntry);
				}
			} else {
				currentFilteredEntries.remove(possibleEntry);
			}
		}
	}
	
	private boolean isFilterMatch(String filterText, String possibleEntry) {
		if (possibleEntry != null) {
			String processedFilterText = filterText.trim().toLowerCase();
			String processedPossibleEntry = possibleEntry.trim()
					.toLowerCase();
			if (processedFilterText.length() == 0
					|| processedPossibleEntry.indexOf(processedFilterText) >= 0) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	protected void doStore() {
		Set<Character> loadedOrderStatus = orderStatusToPageMap.keySet();
		for (char orderStatusKey : loadedOrderStatus) {
			FIXMessageFieldColumnChooserEditorPage chooserPage = orderStatusToPageMap.get(orderStatusKey);			
			List<String> chosenFields = chooserPage.getChosenFieldsList();
			List<Integer> chosenFieldIDs = new BasicEventList<Integer>();
			for (String chosenField : chosenFields) {
				int fieldID = fieldEntryToFieldIDMap.get(chosenField);
				chosenFieldIDs.add(fieldID);
			}
			if (chosenFieldIDs != null && chosenFieldIDs.size() > 0) {
				parser.setFieldsToShow(orderStatusKey, chosenFieldIDs);
			}
		}
	}

	private void removeAllPressed() {
		swap(false);
	}

	/**
	 * Returns this field editor's button box containing the Add, Remove, Up,
	 * and Down button.
	 */
	private Composite getAddRemoveButtonBoxControl(Composite parent) {
		if (addRemoveButtonBox == null) {
			addRemoveButtonBox = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			addRemoveButtonBox.setLayout(layout);
			createAddRemoveButtons(addRemoveButtonBox);
			addRemoveButtonBox.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					addButton = null;
					removeButton = null;
					addAllButton = null;
					removeAllButton = null;
					addRemoveButtonBox = null;
				}
			});

		} else {
			checkParent(addRemoveButtonBox, parent);
		}
		return addRemoveButtonBox;
	}

	private Composite getUpDownButtonBoxControl(Composite parent) {
		if (upDownButtonBox == null) {
			upDownButtonBox = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			upDownButtonBox.setLayout(layout);
			createUpDownButtons(upDownButtonBox);
			upDownButtonBox.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					upButton = null;
					downButton = null;
					upDownButtonBox = null;
				}
			});

		} else {
			checkParent(upDownButtonBox, parent);
		}
		return upDownButtonBox;
	}

	private Table createAvailableFieldsTable(Composite parent) {
		if (availableFieldsTable == null) {
			availableFieldsTable = new Table(parent, SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
			availableFieldsTable.setHeaderVisible(false);
			availableFieldsTable.setFont(parent.getFont());
			availableFieldsTable.addSelectionListener(getSelectionListener());
			availableFieldsTable.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					availableFieldsTable = null;
				}
			});
			TableColumn column;
			column = new TableColumn(availableFieldsTable, SWT.BEGINNING | SWT.H_SCROLL);
			column.setWidth(205);
		} else {
			checkParent(availableFieldsTable, parent);
		}
		return availableFieldsTable;
	}
	
	private Table createChosenFieldsTable(Composite parent) {
		if (chosenFieldsTable == null) {
			chosenFieldsTable = new Table(parent, SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
			chosenFieldsTable.setHeaderVisible(false);
			chosenFieldsTable.setFont(parent.getFont());
			chosenFieldsTable.addSelectionListener(getSelectionListener());
			chosenFieldsTable.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					chosenFieldsTable = null;
				}
			});
			TableColumn column;
			column = new TableColumn(chosenFieldsTable, SWT.BEGINNING | SWT.H_SCROLL);
			column.setWidth(205);
		} else {
			checkParent(chosenFieldsTable, parent);
		}
		return chosenFieldsTable;
	}

	private IndexedTableViewer createTableViewer(Table aTable) {
		IndexedTableViewer tableViewer = new IndexedTableViewer(aTable);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(new String[] { "Field" });

		CellEditor[] editors = new CellEditor[1];
		editors[0] = new TextCellEditor(aTable);
		tableViewer.setContentProvider(new EventListContentProvider<String>());
		tableViewer.setLabelProvider(new FIXMessageFieldChooserLabelProvider());
		return tableViewer;
	}

	protected BasicEventList<String> getNewInputObject(Table aTable) {
		BasicEventList<String> itemsAsList = new BasicEventList<String>();
		TableItem[] selectedItems = aTable.getSelection();
		if (selectedItems != null && selectedItems.length > 0) {
			for (TableItem item : selectedItems) {
				itemsAsList.add(item.getText());
			}
		}
		return itemsAsList;
	}
	
	public int getNumberOfControls() {
		return 4;
	}

	/**
	 * Returns this field editor's selection listener. The listener is created
	 * if nessessary.
	 */
	private SelectionListener getSelectionListener() {
		if (selectionListener == null) {
			createSelectionListener();
		}
		return selectionListener;
	}

	/**
	 * Returns this field editor's shell.
	 * This method is internal to the framework; subclassers should not call
	 * this method.
	 */
	protected Shell getShell() {
		if (addButton == null) {
			return null;
		}
		return addButton.getShell();
	}

	private void removePressed() {
		setPresentsDefaultValue(false);
		int index = availableFieldsTable.getSelectionIndex();

		if (index >= 0) {
			// table.remove(index);
			getCurrentPage().getChosenFieldsList().remove(index);
			selectionChanged();
		}
	}

	private void selectionChanged() {
		if (availableFieldsTable != null) {
			int fromSize = availableFieldsTable.getItemCount();
			addAllButton.setEnabled(fromSize > 0);
		} else {
			addButton.setEnabled(false);
			addAllButton.setEnabled(false);
		}
		
		if (chosenFieldsTable != null) {
			int toSize = chosenFieldsTable.getItemCount();
			removeButton.setEnabled(toSize > 0);
			removeAllButton.setEnabled(toSize > 0);
			upButton.setEnabled(toSize > 0);
			downButton.setEnabled(toSize > 0);
		} else {
			removeButton.setEnabled(false);
			removeAllButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
	}

	public void setFocus() {
		if (availableFieldsTable != null) {
			availableFieldsTable.setFocus();
		}
	}

	/**
	 * Moves the currently selected item up or down.
	 * 
	 * @param up
	 *            <code>true</code> if the item should move up, and
	 *            <code>false</code> if it should move down
	 */
    private void swap(boolean up) {
        setPresentsDefaultValue(false);
        int filteredIndex = chosenFieldsTable.getSelectionIndex();
        int filteredTargetIndex = up ? filteredIndex - 1 : filteredIndex + 1;
        if(filteredTargetIndex < 0) {
            filteredTargetIndex = 0;
        }
        if (filteredIndex >= 0) {
            TableItem[] selection = chosenFieldsTable.getSelection();

            //cl todo:implement multi-select
            if (selection.length != 1) {
    			PhotonPlugin.getMainConsoleLogger().warn("Multi-select has not been implemented for Up/Down button yet.");
            	return;
            }
            String toReplace = (String) selection[0].getData();            	
            
            filteredChosenEntries.remove(filteredIndex);
            filteredChosenEntries.add(filteredTargetIndex, toReplace);
            
			FIXMessageFieldColumnChooserEditorPage currPage = getCurrentPage();
			List<String> chosenFieldsList = currPage.getChosenFieldsList();

            int actualIndex = chosenFieldsList.indexOf(toReplace);
            int actualTargetIndex = up ? actualIndex - 1 : actualIndex + 1;
            if(actualTargetIndex < 0) {
                actualTargetIndex = 0;
            }
            chosenFieldsList.remove(actualIndex );
            chosenFieldsList.add(actualTargetIndex, toReplace);
            
            chosenFieldsTable.setSelection(filteredTargetIndex);
			chosenFieldsTableViewer.refresh(false);	

        }
        selectionChanged();
    }

	private void addAllPressed() {
		swap(true);
	}
	
	private void upPressed() {
		swap(true);
	}
	
	private void downPressed() {
		swap(false);
	}

	protected void refreshOrderType(char newType) {
		orderType = newType;
		doLoadDefault();
	}

}
