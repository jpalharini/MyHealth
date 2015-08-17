package android.palharini.myhealth.fragments.tabs.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.palharini.myhealth.R;
import android.palharini.myhealth.db.entities.Indicator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IndicatorsListAdapter extends BaseAdapter {
	
	static class ViewHolder {
		public TextView tvMeasure, tvUnit, tvDate, tvTime;
		public int position;
	}
	
	private  ViewHolder holder;
	
	private Context context;
	
	private Indicator indicator;
	private List<Indicator> lsIndicators;
		
	public IndicatorsListAdapter(Context context, List<Indicator> lsIndicators) {
		this.context = context;
		this.lsIndicators = lsIndicators;
	}
		
	@Override
	public int getCount() {
		return lsIndicators.size();
	}

	@Override
	public Object getItem(int id) {
		return lsIndicators.get(id);
	}

	@Override
	public long getItemId(int id) {
		return lsIndicators.get(id).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    		
		if (convertView == null) {
		    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		    convertView = inflater.inflate(R.layout.list_indicators, parent, false);	
		      
		    holder = new ViewHolder();
		      
		    holder.tvMeasure = (TextView) convertView.findViewById(R.id.lvTvMeasure);
		    holder.tvUnit = (TextView) convertView.findViewById(R.id.lvTvUnit);
		    holder.tvDate = (TextView) convertView.findViewById(R.id.lvTvDate);
		    holder.tvTime = (TextView) convertView.findViewById(R.id.lvTvTime);
		      
		    convertView.setTag(holder);
	      
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		indicator = lsIndicators.get(position);
		
		if (indicator != null) {
			holder.tvMeasure.setText(indicator.getMeasure1().toString());
			holder.tvUnit.setText(indicator.getUnidade());
			
//			String stDate = df.getDataAndroid(indicator.getData());
//			String stTime = df.getHorarioAndroid(indicator.getHora());
			
			holder.tvDate.setText(indicator.getData());
			holder.tvTime.setText(indicator.getHora());
			
		}

	    return convertView;
	}

}

