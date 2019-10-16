package com.marketcetera.colin.ui.views.desktop;

import org.marketcetera.fix.ActiveFixSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.marketcetera.colin.backend.client.FixAdminClientService;
import com.marketcetera.colin.ui.MainView;
import com.marketcetera.colin.ui.crud.FixAdminDataProvider;
import com.marketcetera.colin.ui.utils.WebUiConst;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

/* $License$ */

/**
 *
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
@Route(value = WebUiConst.PAGE_DESKTOP, layout = MainView.class)
@RouteAlias(value = WebUiConst.PAGE_ROOT, layout = MainView.class)
@PageTitle(WebUiConst.TITLE_DESKTOP)
public class DesktopView
        extends VerticalLayout
{
    @Autowired
    public DesktopView(FixAdminClientService inFixAdminClientService)
    {
        grid = new Grid<>();
        grid.setDataProvider(new FixAdminDataProvider(inFixAdminClientService));
        grid.addColumn(u -> u.getFixSession().getName()).setHeader("Name").setWidth("200px").setFlexGrow(5);
        grid.addColumn(u -> u.getFixSession().getSessionId()).setHeader("Session").setWidth("200px").setFlexGrow(5);
        grid.addColumn(u -> u.getClusterData()).setHeader("Host").setWidth("200px").setFlexGrow(5);
        grid.addColumn(u -> u.getStatus()).setHeader("Status").setWidth("200px").setFlexGrow(5);
        grid.addColumn(u -> u.getSenderSequenceNumber()).setHeader("Sender\nSeq Num").setWidth("200px").setFlexGrow(5);
        grid.addColumn(u -> u.getTargetSequenceNumber()).setHeader("Target\nSeq Num").setWidth("200px").setFlexGrow(5);
        add(grid);
    }
    /**
     * FIX session grid value
     */
    private Grid<ActiveFixSession> grid;
    private static final long serialVersionUID = 7323613882125927022L;
}
