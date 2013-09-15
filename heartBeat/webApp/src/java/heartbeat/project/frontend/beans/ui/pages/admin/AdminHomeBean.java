package heartbeat.project.frontend.beans.ui.pages.admin;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.session.SessionBean;
import org.icefaces.ace.component.chart.Axis;
import org.icefaces.ace.component.chart.AxisType;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.SectorSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * User: Hunea Paul Lucian
 * Date: 9/15/13
 */
@Component
@Scope(Scopes.Request)
public class AdminHomeBean  implements Serializable {

    @Autowired
    SessionBean sessionBean;
    private Axis xAxis = new Axis() {{
        setType(AxisType.CATEGORY);
        setTickAngle(-30);
    }};

    private Axis[] yAxes = new Axis[] {
            new Axis() {{
                setAutoscale(true);
                setTickInterval("5");
                setLabel("Ocupied space <bytes>");
            }}
    };
    public AdminHomeBean() {
    }

    public List<SectorSeries> getPieData(){
        return sessionBean.getNodesForChart();
    }

    public List<CartesianSeries> getBarChart(){
        return sessionBean.getBarChart();
    }

    public Axis getxAxis() {
        return xAxis;
    }

    public void setxAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    public Axis[] getyAxes() {
        return yAxes;
    }

    public void setyAxes(Axis[] yAxes) {
        this.yAxes = yAxes;
    }
}
