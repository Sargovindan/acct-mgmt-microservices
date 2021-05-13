package com.account.management.customer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pics {
	
	public static void main(String[] args) {
		String pics = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n"
				+ "john.png, London, 2015-06-20 15:13:22\n"
				+ "myFriends.png, Warsaw, 2013-09-05 14:07:13\n"
				+ "Eiffel.jpg, Paris, 2015-07-23 08:03:02\n"
				+ "pisatower.jpg, Paris, 2015-07-22 23:59:59\n"
				+ "BOB.jpg, London, 2015-08-05 00:02:03\n"
				+ "notredame.png, Paris, 2015-09-01 12:00:00\n"
				+ "me.jpg, Warsaw, 2013-09-06 15:40:22\n"
				+ "a.png, Warsaw, 2016-02-13 13:33:50\n"
				+ "b.jpg, Warsaw, 2016-01-02 15:12:22\n"
				+ "c.jpg, Warsaw, 2016-01-02 14:34:30\n"
				+ "d.jpg, Warsaw, 2016-01-02 15:15:01\n"
				+ "e.png, Warsaw, 2016-01-02 09:49:09\n"
				+ "f.png, Warsaw, 2016-01-02 10:55:32\n"
				+ "g.jpg, Warsaw, 2016-02-29 22:13:11";
        // write your code in Java SE 8
		Pics p = new Pics();
		System.out.print(p.solution(pics));
	}
	
	
	public String solution(String S) {
		
		
		return order(S);
    }
	
	 class PicData {
		String cityName;
		Date timeStamp;
		String ext;
		
		public PicData(String cityName,Date timeStamp,String ext) {
			this.cityName = cityName;
			this.timeStamp = timeStamp;
			this.ext = ext;
		}
	}
	
	public static String order(String pics) {
		
		Map<String, List<Date>> dateMaps = createOrderedDateMap(pics);
		String lines[] = pics.split("\\r?\\n");
		StringBuffer res = new StringBuffer();
		
		for (int i = 0; i < lines.length; i++) {
			String[] line = lines[i].split(",");
			//System.out.println("cityName ===="+cityName);
			
			String cityName = line[1];
			//Get third elem as timestamp
			String timeStamp = line[2];
			//System.out.println("timeStamp ===="+timeStamp);
			
			//Get first elem as pic name
			String name = line[0];
			//System.out.println("name ===="+name);
			
			//Split as pic name for ext
			String ext = name.substring(name.lastIndexOf("."),name.length());
			//System.out.println("ext ===="+ext);
			
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(timeStamp);
				int idx = dateMaps.get(cityName).indexOf(date);
				//System.out.println("idx ===="+idx);
				String newName = cityName+""+padLeftZeros(""+(idx+1), String.valueOf(dateMaps.get(cityName).size()).length())+ext;
				//System.out.println("newName ===="+newName);
				res.append(newName);
				//res.append(" "+date);
				res.append("\n");
				//System.out.println("timeStamp ===="+timeStamp);
				
				//Pics.PicData data = new Pics().new PicData(cityName, date, ext);
				//picDatas.add(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return res.toString();
	}


	private static Map<String, List<Date>> createOrderedDateMap(String pics) {
		Map<String, List<Date>> dateMaps = new HashMap<>();
		//Split by newline
		String lines[] = pics.split("\\r?\\n");
		//System.out.println("pics cnt ===="+lines.length);
		//Split each line by commas
		for (int i = 0; i < lines.length; i++) {
			String[] line = lines[i].split(",");
			//Get second elem as city name
			String cityName = line[1];
			String timeStamp = line[2];
			if(dateMaps.containsKey(cityName)) {
				List<Date> dates = dateMaps.get(cityName);
				try {
					dates.add(new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(timeStamp));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Collections.sort(dates, new Comparator<Date>() {
				    @Override
				    public int compare(Date o1, Date o2) {
				        return o1.compareTo(o2);
				    }
				});
				dateMaps.put(cityName, dates);
			}else {
				List<Date> dates = new ArrayList<Date>();
				try {
					dates.add(new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(timeStamp));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dateMaps.put(cityName,dates);
			}
		}
		return dateMaps;
	}
	
	public static String padLeftZeros(String inputString, int length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append('0');
	    }
	    sb.append(inputString);

	    return sb.toString();
	}
	
	public class CustomComparator implements Comparator<PicData> {
	    @Override
	    public int compare(PicData o1, PicData o2) {
	        return o1.timeStamp.compareTo(o2.timeStamp);
	    }
	}
	
	public class DateCustomComparator implements Comparator<Date> {
	    @Override
	    public int compare(Date o1, Date o2) {
	        return o1.compareTo(o2);
	    }
	}

}
