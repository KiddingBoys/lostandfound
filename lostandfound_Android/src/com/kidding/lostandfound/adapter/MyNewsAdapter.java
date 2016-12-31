package com.kidding.lostandfound.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.activitys.ClassifySameOneActivity;
import com.kidding.lostandfound.activitys.NewsDetailActivity;
import com.kidding.lostandfound.request.NewsContent;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-10 ����11:35:05 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyNewsAdapter extends BaseAdapter {
	private Context mContext;
	private Intent intent;
	private ArrayList<NewsContent> newsList;


	public MyNewsAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		NewsContent newsContent; 
		newsList = new ArrayList<NewsContent>();
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic04.png"
				, "У�������ϻ�ٰ조��������һ��ʫ�����б���", "2016-05-07", "5��7�����磬����У�������ϻ����죬�������Э�ᡢʫ������Э�ᡢ����Э�Ṳͬ�а�ġ���������һ��ʫ�����б����������ĵ�¥411��412����˳����չ��������ΪA��B����������60��ѡ�ֲ�����ͨ����ί�ǵ�����ѡ�Σ�����ѡ��34��ѡ�ֽ��븴�������α���ͨ���������ֶ��صĲ�����ʽ������ͬѧ�ǶԴ���ĸ��̺Ͷ���������������ʽ��ϲ����ͬʱ��ҲΪ��Уѧ���ṩ��һ�������Լ����Ա��������ƽ̨�����չ�ֳ���ѧ���ķ�ɡ�");		
		newsList.add(newsContent);
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic03.png"
				, "��У�ٰ�ɽ��ʡ�����조�ٳ���������������У����ͬ��ѧר��������", "2016-05-06", "5��6�����磬��У�ڿƼ��������ٰ�ɽ��ʡ�����조�ٳ���������������У����ͬ��ѧר������������ͬ�о���������쵼����ѧԺ�������������ĸ���Ա����ѧԺѧ�������600�˲μ��������ᡣɽ��ʡ����ί����Ա��������̫������ί��ί�����䲿��ί����ͬ־���˾��ʵ��������档ѧ�������������������˱���ᡣ");		
		newsList.add(newsContent);
	
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic02.png"
				, "ͬ����ϵ�н���-��������ʷ��̸��׼����������淶������", "2016-04-28", "4��28�գ��й�����ѧԺ����ʷ���о�Ա�����������¥���㱨����������Ϊ��������ʷ��̸��׼����������淶����ר�⽲���� ����ڶ��й��ִ�������ʷ��չ���̡��й�����ʷѧ��������ȵ㡢�й�����ʷ�о������ڵ����⡢�й�����ʷ��ѧ�ƽ�����ĸ��������˲���������������ʷƾ��������ԡ��ʻ��ԡ��������ܵ�������ڵĻ�ӭ��̸��������ʷ�����ʷ�ϵķ�������ʱ��˵��������ʷ�������ǿ���ʷ�ϵ��ռ������������Ұ����˶���Щ���Ͻ��в��͵ĸ��˹۵㣬��Щ�۵���ͬѧ�Ƕ�Ŀһ�¡���Կ�����ʷ��ʵ�Ե����⣬�����˵ֻҪ���о������б��ֽ��ȸС����з��׾���ץס��ʷ��ʵ��������ʵ��������ʵ���ı���ʵ����ʹ������ʷ���о��������ӹ淶��");		
		newsList.add(newsContent);
		
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic01.png"
				, "�̿�ѧԺ12��ʵϰ����", "2016-03-01", "��ѧ��һ�ܣ�ѧԺ��2012��ѧ����ʵϰ�����˰��ţ�����ʵϰ��ѧ�������������������Ƽ�ר����������Сѧ����רҵ��264�ˣ����䵽�˱�ʦ���С�������ѧ����ͬ�е�ʮ��СѧУ����ͬ��ʵ��Сѧ�����ڵ�ʮ������СѧУ��ѧԺ�Դ��ӽ�ʦ��ָ����ʦ��ѧ�������Ҫ��Ҫ��������ʵϰ����������ʵϰ���ɣ�ȷ��ʵϰȡ��ʵЧ������ʵϰ���ӽ�ʦ��ѧ��ȫ���͵���ʵϰѧУ��ʵϰѧУ��ѧ����ʵϰ���˰��ţ�ָ����ָ����ʦ��ѧ��Ϊ�������µ�ʵϰ��ʽ��ʼ�ˡ�");		
		newsList.add(newsContent);
	
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsList.get(position);///
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MyViewHolder myHolder = MyViewHolder.getViewHolder(mContext
				, convertView, parent, R.layout.item_news_listitem, position);
		
		TextView newsTitleTv = (TextView) myHolder.getConvertView().findViewById(R.id.tv_news_title);
		TextView newsTimeTv = (TextView) myHolder.getConvertView().findViewById(R.id.tv_news_time);
		TextView newsContentTv = (TextView) myHolder.getConvertView().findViewById(R.id.tv_news_content);
		ImageView newsIv = (ImageView) myHolder.getConvertView().findViewById(R.id.iv_news_pic);
		RelativeLayout itemRl = (RelativeLayout) myHolder.getConvertView().findViewById(R.id.rl_news_item);
		
//		iv.setBackgroundResource(imgs[position]);

		final int i =  position;
		
		newsTitleTv.setText(newsList.get(position).getNewsTitle());
		newsTimeTv.setText(newsList.get(position).getNewsTime());
		newsContentTv.setText(newsList.get(position).getNewsContent());
		Glide.with(mContext).load(newsList.get(position).getNewsImage())
		.placeholder(R.drawable.icon_news_background).centerCrop().into(newsIv);
		
		final String newsimg = newsList.get(position).getNewsImage();
		final String newscontent = newsList.get(position).getNewsContent();
		final String newstitle = newsList.get(position).getNewsTitle();
		
		itemRl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(mContext,NewsDetailActivity.class);
				intent.putExtra("newstitle",newstitle);
				intent.putExtra("newscontent",newscontent);
				intent.putExtra("newsimg",newsimg);
				mContext.startActivity(intent);
			}
		});
		
		return myHolder.getConvertView();
	}

}