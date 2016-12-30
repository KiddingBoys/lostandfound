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
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-10 上午11:35:05 
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
				, "校社团联合会举办“春天送你一首诗”朗诵比赛", "2016-05-07", "5月7日下午，由我校社团联合会主办，清风朗诵协会、诗歌朗诵协会、读书协会共同承办的“春天送你一首诗”朗诵比赛初赛于文德楼411和412教室顺利开展。比赛分为A、B两场，共有60名选手参赛。通过评委们的认真选拔，最终选出34名选手进入复赛。本次比赛通过朗诵这种独特的阐述方式，表达出同学们对春天的歌颂和对朗诵这种艺术形式的喜爱。同时，也为我校学生提供了一个锻炼自己语言表达能力的平台，充分展现出大学生的风采。");		
		newsList.add(newsContent);
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic03.png"
				, "我校举办山西省第三届“百场国防教育宣讲高校”大同大学专场宣讲会", "2016-05-06", "5月6日下午，我校在科技报告厅举办山西省第三届“百场国防教育宣讲高校”大同大学专题宣讲会活动。大同市军分区相关领导、各学院负责征兵工作的辅导员及各学院学生代表近600人参加了宣讲会。山西省国教委宣讲员、晋中市太谷县县委常委、人武部政委蒋勇同志做了精彩的宣讲报告。学生处副处长杨容主持了报告会。");		
		newsList.add(newsContent);
	
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic02.png"
				, "同大讲堂系列讲座-“口述历史访谈的准备、程序与规范”举行", "2016-04-28", "4月28日，中国社会科学院近代史所研究员左玉河于善美楼六层报告厅作主题为《口述历史访谈的准备、程序与规范》的专题讲座。 左教授对中国现代口述历史发展历程、中国口述史学界的理论热点、中国口述史研究所存在的问题、中国口述史的学科建设等四个方面作了阐述，表明口述历史凭借其揭秘性、鲜活性、活泼性受到广大受众的欢迎。谈及口述历史与口述史料的分歧问题时他说，口述历史不仅仅是口述史料的收集和征集，而且包括了对这些资料进行阐释的个人观点，这些观点让同学们耳目一新。针对口述历史真实性的问题，左教授说只要在研究过程中保持紧迫感、秉承奉献精神、抓住历史真实、记忆真实、叙述真实、文本真实就能使口述历史的研究工作更加规范。");		
		newsList.add(newsContent);
		
		newsContent = new NewsContent(
				"http://smartdresspic.oss-cn-beijing.aliyuncs.com/newspic01.png"
				, "教科学院12级实习报道", "2016-03-01", "开学第一周，学院对2012级学生的实习做出了安排，今年实习的学生，包括教育技术本科及专升本、心理、小学教育专业的264人，分配到了北师大附中、北岳中学、大同市第十八小学校、大同市实验小学等市内的十二所中小学校，学院对带队教师、指导教师、学生提出了要求，要求大家重视实习工作，遵守实习纪律，确保实习取得实效。本周实习带队教师将学生全部送到了实习学校，实习学校对学生的实习做了安排，指定了指导教师，学生为期两个月的实习正式开始了。");		
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