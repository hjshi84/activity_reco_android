package com.example.activity_reco;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Path;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.webkit.DateSorter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Callback{

	public String emernum="13916945735";//the emergency phone number
	public String connectperson="8613916945735";//The person smartphone number who will receive the message
	
	public static String hostip;             //IP  
    public static String hostmac;            //MAC  
    public static int hostport=8888;		 //PORT
    
    public double lat;						//latitude and Longitude of the location
    public double lng;
    
    private SensorManager sensMGr;           //Sensor Part
    
    private android.hardware.Camera aCamera; //Camera part
    private PreviewCallback mJpegPreviewCallback;
    private SurfaceView mSurfaceview;
    private SurfaceHolder mSurfaceHolder;
    private int[] result=new int[1000];
    private int inum=0;
    private boolean previewrun;
    private long[] mTimer=new long[1000];
	private int[] datas={142801,149984,152805,148124,144826,140130,161997,163611,145068,172612,146963,161736,161032,160715,151213,164041,162019,152064,158619,148531,160840,161306,163005,159295,158504,164157,152142,157460,153761,150321,157573,160821,160019,152390,156350,159901,153803,159145,157172,156116,149461,157047,155272,158819,177928,159409,162087,160911,164386,152500,155333,154847,172979,169865,180999,148483,161428,173115,170584,170231,176225,163987,170608,165074,172036,162416,169882,154988,162211,169992,174604,161484,154709,161173,162531,163872,159534,160176,163205,176583,194210,170430,173048,175323,169720,166622,172153,156619,169754,163617,174096,171160,203693,210484,197687,204218,225127,237261,215183,217647,212349,223375,233741,252184,236588,247906,260276,239582,240342,245751,250032,249042,256334,251624,245472,250529,246295,278026,263498,254166,244890,267466,276663,267094,256231,241710,257446,265356,250635,253298,245663,255924,252057,258867,250162,287216,256186,244957,262114,254463,258751,269227,262509,250423,261072,253811,290782,268205,260436,281512,258467,278997,271916,279616,252923,267389,249457,262066,269687,263056,258320,263060,258220,257243,261271,252463,266336,261886,274453,299940,283876,275958,265837,257234,280407,281804,292965,283653,263340,283437,288735,272581,290005,266729,266655,258426,288706,290337,281826,266504,263812,272223,283558,265855,261631,272831,273322,277298,292310,266949,271374,274000,268526,280132,292799,285246,272246,268080,269777,282307,283300,285656,288971,298459,278530,304141,275050,301089,269371,289804,288916,288123,262181,273540,292177,280423,290299,276121,287450,274772,309028,299633,283487,278001,276353,281016,295155,292581,292102,291399,281044,289697,283948,297142,304839,270266,287199,310092,288114,303778,287244,286760,305354,293170,287215,281790,307922,307096,288186,306635,284820,289297,319924,320097,300744,297596,317663,298026,303015,293306,305441,314363,283390,323127,304279,296122,310090,292432,298208,293873,298130,296026,295608,302597,298544,307472,318991,295224,310703,311813,312346,293646,304058,315658,296837,307228,299817,294243,295933,302237,315322,302259,301590,289136,327322,306926,307933,309576,308382,290249,312132,308013,288520,301983,314223,329375,307729,319185,302520,298510,332269,322168,328528,313824,304083,297570,307867,308257,310178,318187,307784,315272,325528,322442,314661,294940,292670,326645,318363,313121,295505,302732,303886,308921,319741,323913,323863,334983,353450,306025,304989,316419,312569,317532,316188,331583,320440,316553,318029,331076,330446,347421,327105,334519,327913,321412,328359,317947,317572,330850,337211,331252,325543,325831,336055,337648,325521,311748,312638,323977,344414,351125,340087,335309,347005,345274,358481,338984,344173,328988,338973,344698,346164,363639,333837,349216,340724,349108,354186,327740,369667,330019,327139,322271,337804,324180,328428,345437,339700,331107,332634,328121,326820,359083,356999,352942,367990,353921,350479,350777,351130,354088,341700,333326,357883,356848,352618,366644,348827,346062,355372,355033,346061,362329,374429,338225,347137,357502,346917,352238,336809,363124,369823,354075,370378,370315,343702,352615,356164,360176,354662,366230,344385,357886,340604,351385,359419,350974,333757,345637,349407,369554,364376,365947,369374,339934,352722,350766,361666,376315,348570,373751,360427,358636,350596,346012,365008,374081,384183,369071,378126,366877,362927,356088,369216,393145,384025,360040,360180,387175,376587,358950,388687,382917,356742,377932,355266,366119,360350,351938,375074,358162,342980,355989,369714,369928,373614,359135,363007,368305,362348,368374,375062,378848,362102,385708,372566,390112,357414,361526,353606,386739,386369,361795,356056,361407,383004,368931,365820,360866,366580,376976,360176,402362,374798,380556,343614,380300,386962,364467,387163,398738,386463,357283,400549,360930,370900,404053,388418,365103,383088,382884,405781,385220,373192,373337,385225,371263,382172,393045,379810,381609,380012,394220,372101,388655,379039,377412,389125,405293,379985,385865,390347,380007,382835,400553,376745,387936,380703,395864,414988,411639,404893,403887,405060,405468,372228,398037,386195,409748,412634,419749,377822,396955,398006,429596,384653,409494,382571,399382,417154,410367,394140,403684,400166,405684,397935,399873,425499,399975,423545,409336,405599,402625,431168,416984,416177,396688,393502,405399,412213,413840,395485,432197,416796,409272,418401,402318,418177,414135,413431,406267,393974,412095,398968,405604,421769,406500,409048,403522,420150,417435,412132,434154,421825,424112,435995,436687,424108,415382,417379,437694,431500,423946,400554,409387,435137,413581,419911,430073,432443,425646,434453,426361,420587,419688,425262,423402,414410,438371,420491,434538,424139,404422,436344,433470,419485,421348,401220,429600,436062,426543,416510,427639,433766,419748,420809,450789,414907,436624,422357,433216,437924,416350,408588,433949,444219,460997,414270,427520,428758,438981,437700,440807,440605,429485,452264,426374,421295,429370,410188,414741,439333,446802,452257,426530,426738,437391,424808,421009,442820,445509,407371,413148,424698,427182,416601,436942,481764,442147,440317,472855,446878,432042,427628,438193,464575,459117,433810,447218,427723,450780,443283,441614,435075,440319,422607,428080,439427,414780,435088,443926,440038,428571,453423,435347,421947,459584,457170,441366,441817,421263,437630,445164,455970,457707,438068,458476,413084,432489,448281,471152,454935,448241,460262,449227,441813,452778,459406,430157,449157,458088,444283,465167,452817,439809,451043,466946,443123,435766,458343,470322,451906,441765,455221,452474,433796,447761,447802,472028,444086,449566,435757,438725,485278,470632,445776,465676,475113,468586,480360,466611,451491,464225,460550,453020,444686,460186,462587,447434,459730,444298,464951,468522,459170,467500,464570,457642,463908,464286,466191,463520,471412,446155,457210,461899,461547,469003,465873,459852,464599,458726,477137,454815,472335,466960,472863,452441,468376,461820,462324,463851,469976,456214,470522,465349,459411,458371,462045,450752,465759,447953,479139,436832,461599,446096,481971,487430,478021,480745,471067,473925,457790,455569,446443,477767,478356,476259,474151,464514,479983,491367,496004,473343,499228,484491,497604,490891,474439,476497,462139,482228,493957,456905,467497,476177,512968,482793,489646,480811};	
	
	private boolean deletedata=true;//delete the former 50 data
	
	private SurfaceView picSurfaceView;
	private SurfaceHolder picSurfaceHolder;
	
	
    private Handler messageHandler;
    private Timer alertTimer=null;
    private StringBuilder mesBuilder=null;
    TextView textcontent;
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  

        setTitle("To my lover---yihusmiling");
        
        textcontent=(TextView)findViewById(R.id.content);
        TextView tv= (TextView)findViewById(R.id.config);  
        mesBuilder=new StringBuilder();
        
        WifiManager wifiMgr = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
        if (null != info) {
            hostmac = info.getMacAddress();
            hostip = intToIp(info.getIpAddress());
        }
        
        /*  show IP, MAC and port */          
        tv.setText("HostIP:" + hostip +"    HostPort:"+hostport+ "    HostMAC:" + hostmac);      
        
        findpeaksnum(datas, 7, datas[899]/150);
        
        
        /*open tcpip server*/
        /*Thread desktopServerThread = new Thread(new SocketServer());  
        desktopServerThread.start();  
        Looper looper = Looper.myLooper();
        messageHandler = new MessageHandler(looper);*/
        
        /*sensor part*/
        /*sensMGr=(SensorManager)getSystemService(Context.SENSOR_SERVICE);       
        Sensor mAccelerometer=sensMGr.getDefaultSensor(Sensor.TYPE_LIGHT);   
        sensMGr.registerListener(senListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);*/
        
        /*camera part*/
       mSurfaceview = (SurfaceView) this.findViewById(R.id.msurfaceView);  
       mSurfaceHolder = mSurfaceview.getHolder(); // 绑定SurfaceView，取得SurfaceHolder对象  
       mSurfaceHolder.addCallback(this); // SurfaceHolder加入回调接口  
        // mSurfaceHolder.setFixedSize(176, 144); // 预览大小設置  
       mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 設置顯示器類型，setType必须设置  
       android.view.ViewGroup.LayoutParams lp = mSurfaceview.getLayoutParams();//调整显示大小
       lp.width = 1;
       lp.height = 1;
       mSurfaceview.setLayoutParams(lp);

       mJpegPreviewCallback = new PreviewCallback() {
			
			@Override
			public void onPreviewFrame(byte[] data, android.hardware.Camera camera) {
				// TODO Auto-generated method stub
				try  
			     {  
					//delete 50 frame
				  if (deletedata&&inum<50) {
					  inum++;
					  return;
				  }
				  else if(deletedata){
					  inum=0;
					  deletedata=false;
				  }
				  //decode the frame, from yuv420sp to rgb
			      int rgb[]=new int[640*480];
			      decodeYUV420SP(rgb, data, 640, 480);
			      //get the red part of the frame
			      int thisresult=0;
			      for(int temp:rgb){
			    	 thisresult+= (((temp & 0x00ff0000)>>16)&0x0ff);
			      }
			      //get the system time and result
			      mTimer[inum]=System.currentTimeMillis();
			      result[inum]= thisresult;
			      //refresh the picsurface holder
			      updatepicsurfaceholder(picSurfaceHolder,inum++);
			      
			      //get 900 frame then stop, calculate the heart rate and show the answer
			      if (inum>=900){
			    	  	aCamera.setPreviewCallback(null);
			    	  	long durtime=System.currentTimeMillis()-mTimer[0];
			    	  	Log.i("time", durtime+"");
			    	  	Log.i("something",(int)(inum/((durtime/1000)*3))+"");
			    	  	//get the heart frequency
			    	  	ArrayList<findpeakdata> temps=findpeaksnum(result,(int)(inum/((durtime/1000)*3)),thisresult/150);
			    	  	int sum=updatepicsurfaceholder(picSurfaceHolder, temps,inum-1);
			    	  	Log.i("sum",sum+"");
			    	  	//calculate the 60s result and show it 
			    	  	textcontent.setText("your heart rate is: "+sum*60*1000/durtime+"");
			    	  	//write the results to the file
			    	  	String total = null;
			    	  	for(int temp:result){
			    	  		total=total+" "+temp;
			    	  	}
			    	    writeFile(total);
			    	    //vibrate the phone
			    	  	Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
			    	  	vibrator.vibrate(1000);
			    	  	Log.i("end","end");
			    	  	//release the camera
			    	  	aCamera.release();
			    	  	aCamera=null;
			    	  	inum=0;
			      }
			        
			     } catch (Exception e)  
			     {  
			      Log.v("System.out", e.toString());  
			     }     
			    }


			public void writeFile(String writestr) throws IOException{   
				  try{   
				
					  
				        FileOutputStream fout =new FileOutputStream(Environment.getExternalStorageDirectory()+"/note.txt");   
				  
				        byte [] bytes = writestr.getBytes();   
				  
				        fout.write(bytes);   
				  
				        fout.close();   
				      }   
				  
				        catch(Exception e){   
				        e.printStackTrace();   
				       }   
				}
			
			
			
			public void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
			    final int frameSize = width * height;

			    for (int j = 0, yp = 0; j < height; j++) {
			        int uvp = frameSize + (j >> 1) * width,  v = 0;//u = 0,
			        for (int i = 0; i < width; i++, yp++) {
			            int y = (0xff & ((int) yuv420sp[yp])) - 16;
			            if (y < 0) y = 0;
			            if ((i & 1) == 0) {
			                v = (0xff & yuv420sp[uvp++]) - 128;
			   //             u = (0xff & yuv420sp[uvp++]) - 128;
			            }

			            int y1192 = 1192 * y;
			            int r = (y1192 + 1634 * v);
			    //        int g = (y1192 - 833 * v - 400 * u);
			   //         int b = (y1192 + 2066 * u);

			            if (r < 0) r = 0; else if (r > 262143) r = 262143;
			    //        if (g < 0) g = 0; else if (g > 262143) g = 262143;
			    //        if (b < 0) b = 0; else if (b > 262143) b = 262143;

			            rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) ;//| ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			        }
			    }
			}

		};
      
		
		/*draw pic part*/
		picSurfaceView=(SurfaceView)this.findViewById(R.id.picsurfaceView);
		
		picSurfaceHolder=picSurfaceView.getHolder();
		picSurfaceHolder.addCallback(new picholdercallback());
		picSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		
    }  
    
    
    private int updatepicsurfaceholder(SurfaceHolder holder,ArrayList<findpeakdata> temps,int num) {
    	Canvas mCanvas=holder.lockCanvas();
		Paint mPaint=new Paint();
		mPaint.setColor(Color.RED);
		int width=mCanvas.getWidth();
		int height=mCanvas.getHeight();
		if(width>950) width=950;
		if(height>550) height=550;
		int min=2147483647,max=0;
		for(int i=0;i<=num;i++){
			if (min>result[i]) min=result[i];
			if (max<result[i]) max=result[i];
		}
		float detaY=(float)(max-min)/(height-50);
		float detaX=(float) (900.0/(width-50));
		
		int sum=0;
	  	for(int i=0;i<temps.size();i++){
	  		if (temps.get(i).status==0) {
	  			sum++;
	  			mCanvas.drawCircle(temps.get(i).position/detaX+25,525-(temps.get(i).data-min)/detaY,5 ,mPaint);
	  			Log.e("position",temps.get(i).position+"");
	  		}
	  	}
	  	holder.unlockCanvasAndPost(mCanvas);
	  	return sum;
    }
	private void updatepicsurfaceholder(SurfaceHolder holder,int num) {
		// TODO Auto-generated method stub
		if (num<1) return;
		Canvas mCanvas=holder.lockCanvas();
		int width=mCanvas.getWidth();
		int height=mCanvas.getHeight();
		if(width>950) width=950;
		if(height>550) height=550;
		Paint mPaint=new Paint();
		mPaint.setColor(Color.BLUE);
		mCanvas.drawARGB(255, 255, 255, 255);
		mCanvas.drawLine(25, 25, 25, height-25, mPaint);
		mCanvas.drawLine(25, height-25, width-25, height-25, mPaint);
		Path mPath=new Path();
		mPath.moveTo(20, 25);
		mPath.lineTo(30, 25);
		mPath.lineTo(25, 20);
		mPath.close();
		mPath.moveTo(width-25, height-30);
		mPath.lineTo(width-25, height-20);
		mPath.lineTo(width-20, height-25);
		mPath.close();
		mCanvas.drawPath(mPath,mPaint);
		
		int min=2147483647,max=0;
		for(int i=0;i<=num;i++){
			if (min>result[i]) min=result[i];
			if (max<result[i]) max=result[i];
		}
		float detaY=(float)(max-min)/(height-50);
		float detaX=(float) (900.0/(width-50));
		for(int i=1;i<=num;i++){
			mCanvas.drawLine(25+i/detaX, height-25-(result[i-1]-min)/detaY, 26+i/detaX,height-25-(result[i]-min)/detaY, mPaint);
		}
		holder.unlockCanvasAndPost(mCanvas);
	}
    
    class picholdercallback implements Callback{
    	
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			initpic(holder);
		}


		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			initpic(holder);
		}

		private void initpic(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Canvas mCanvas=holder.lockCanvas();
		//	mCanvas.drawColor(Color.WHITE);
		//	Bitmap mbitmap = Bitmap.createBitmap(950, 150, Bitmap.Config.ARGB_8888); 
		//	mCanvas.setBitmap(mbitmap);
			int width=mCanvas.getWidth();
			int height=mCanvas.getHeight();
			if(width>950) width=950;
			if(height>550) height=550;
			Paint mPaint=new Paint();
			mPaint.setColor(Color.BLUE);
			mCanvas.drawARGB(255, 255, 255, 255);
			mCanvas.drawLine(25, 25, 25, height-25, mPaint);
			mCanvas.drawLine(25, height-25, width-25, height-25, mPaint);
			Path mPath=new Path();
			mPath.moveTo(20, 25);
			mPath.lineTo(30, 25);
			mPath.lineTo(25, 20);
			mPath.close();
			mPath.moveTo(width-25, height-30);
			mPath.lineTo(width-25, height-20);
			mPath.lineTo(width-20, height-25);
			mPath.close();
			mCanvas.drawPath(mPath,mPaint);
			holder.unlockCanvasAndPost(mCanvas);
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    

    
    class findpeakdata{
		int data;
		int position;
		int status=0;
	}
	
	public ArrayList<findpeakdata> findpeaksnum(int[] data,int mindist,int minpeak){
		ArrayList<findpeakdata> results=new ArrayList<findpeakdata>();
		ArrayList<Integer> posArrayList=new ArrayList<Integer>();
		if (data.length<=1) {
			findpeakdata temp=new findpeakdata();
			temp.data=data[0];
			results.add(temp);
			return results;
		}
		
		for(int i=1;i<data.length-1;i++){
			if (data[i]>data[i-1]&&data[i]>data[i+1]){
				findpeakdata temp=new findpeakdata();
				temp.data=data[i];
				temp.position=i;
				temp.status=0;
				results.add(temp);
			}
		}
		for(int i=0;i<results.size()-1;i++){
			//if (results.get(i).status==-1) continue;

			for(int j=0;j<mindist;j++){
				if(results.get(i).position+j>data.length-1) break;
				if(results.get(i).data<data[results.get(i).position+j]){
					results.get(i).status=-1;
					break;
				}
			}
			for(int j=0;j<mindist;j++){
				if(results.get(i).position-j<0) break;
				if(results.get(i).data<data[results.get(i).position-j]){
					results.get(i).status=-1;
					break;
				}
			}
			/*for(int j=i+1;j<results.size();j++){
				if(results.get(j).position-results.get(i).position>mindist) break;
				if (results.get(j).status==-1) continue;
				else{
					if(results.get(i).data<results.get(j).data){
						results.get(i).status=-1;
						break;
					}else{
						results.get(j).status=-1;
					}
				}
			}
			for(int j=i-1;j>0;j--){
				if(results.get(i).position-results.get(j).position>mindist) break;
				if(results.get(i).data<results.get(j).data){
					results.get(i).status=-1;
					break;
				}
			}*/
			if (results.get(i).status==0){
				posArrayList.add(i);
			
			}
		}
		for(int i=1;i<posArrayList.size()-1;i++){
			int minlow=2147483647;
			for(int j=results.get(posArrayList.get(i-1)).position;j<results.get(posArrayList.get(i)).position;j++){
				if(minlow>data[j]) minlow=data[j];
			}
			for(int j=results.get(posArrayList.get(i)).position;j<results.get(posArrayList.get(i+1)).position;j++){
				if(minlow>data[j]) minlow=data[j];
			}
			if(results.get(posArrayList.get(i)).data-minlow<results.get(posArrayList.get(i)).data/150) 
				results.get(posArrayList.get(i)).status=-1;
		}
		posArrayList.clear();
		return results;
	/*	int[] results=new int[data.length/2];
		int[] position=new int[data.length/2];
		int[] status=new int[data.length/2];
		int tempnum=0;
		for(int i=1;i<data.length-1;i++){
			if (data[i]>data[i-1]&&data[i]>data[i+1]);
			results[tempnum]=data[i];
			position[tempnum]=i;
			tempnum++;
		}

		for(int i=0;i<data.length-1;i++){
			if (status[i]==-1) continue;
			for(int j=i;j<data.length-1-i;j++){
				if(position[j]-position[i]>mindist) break;
				if (status[j]==-1) continue;
				else{
					if(results[i]<results[j]){
						status[i]=-1;
						break;
					}else{
						status[j]=-1;
					}
				}
			}
		}
		return null;*/
	}
    
    private final SensorEventListener senListener=new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			
	//		Log.i("fuck",event.values[SensorManager.DATA_X]+";"+event.values[SensorManager.DATA_Y]+";"+event.values[SensorManager.DATA_Z]);
			Log.i("fuck",event.values[0]+"");
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
   
      
     class MessageHandler extends Handler {

    	private final LocationListener locationListener = new LocationListener() {
      	  public void onLocationChanged(Location location) {
      		  updateWithNewLocation(location);
      	  }

      	  public void onProviderDisabled(String provider) {
      		  updateWithNewLocation(null);
      	  }

      	  public void onProviderEnabled(String provider) {
      	  }

      	  public void onStatusChanged(String provider, int status, Bundle extras) {
      	  }
      	 };

      	 private void updateWithNewLocation(Location location) {
      		 
      		 if (location != null) {
      			   lat = location.getLatitude();
      			   lng = location.getLongitude();
      			   Log.i("无法获取地理信息","第一个为"+lat+"第二个为"+lng);
      			  } else {
      			   Log.i("无法获取地理信息","无法获取地理信息");
      			  }

      	 }

		public MessageHandler(Looper looper) {

            super(looper);

        }

        @Override

        public void handleMessage(Message msg) {
        	mesBuilder=mesBuilder.append((String)msg.obj+"\n");
            textcontent.setText(mesBuilder);
            if(alertTimer==null){
            	if (((String)msg.obj).contains("falling")){
            		      		
            		
            		alertTimer=new Timer();
            		alertTimer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try{
								Looper.prepare();  
								LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
							        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
								Criteria criteria = new Criteria();
						        criteria.setAccuracy(Criteria.ACCURACY_FINE);
						        criteria.setAltitudeRequired(false);
						        criteria.setBearingRequired(false);
						        criteria.setCostAllowed(true);
						        criteria.setPowerRequirement(Criteria.POWER_LOW);
						        String provider = lm.getBestProvider(criteria, true);
						        
						        Location location = lm.getLastKnownLocation(provider);
						        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
						        updateWithNewLocation(location);   

								//Send SMS
								SmsManager smsManager = SmsManager.getDefault();
								List<String> divideContents = smsManager.divideMessage("HELP! I am in latitude"+lat+";longitude="+lng);  
								for (String text : divideContents) {  
									smsManager.sendTextMessage(connectperson, null, text, null, null);  
								}
							
								//Call emergency
								Intent phoneIntent = new Intent("android.intent.action.CALL",

										Uri.parse("tel:" + emernum));
							
								startActivity(phoneIntent);	
								lm.removeUpdates(locationListener); 
								
								
							}catch(Exception e){
								Looper.prepare();
								Log.e("wrong", e.toString());
								Toast.makeText(getBaseContext(), "there is something wrong,i can not send sms and call number", Toast.LENGTH_SHORT).show();
								
							}
							 
							alertTimer.cancel();
							alertTimer.purge();
							alertTimer=null;						
							System.gc();
							Looper.loop();

						}
					},10000);
            		
            		
            	}
           }else{
        	   if (!((String)msg.obj).contains("falling")){
        		   alertTimer.cancel();
        		   alertTimer=null;
        	   }
     
		}

        }

    }
    
    class SocketServer implements Runnable{
        
        ServerSocket sever;
        Socket socket;
 
       
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {  
	            System.out.println("S: Connecting...");  
	  
	            ServerSocket serverSocket = new ServerSocket(8888);  
	            while (true) {  
	                  
	                //等待接受客户端请求   
	                Socket client = serverSocket.accept();  
	                   
	                System.out.println("S: Receiving...");  
	                  
	                try {  
	                    //接受客户端信息  
	                	 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
	                	 Message message = Message.obtain();
	                     message.obj = in.readLine();
	                     messageHandler.sendMessage(message);

	                } catch (Exception e) { 
	                    System.out.println("S: Error");  
	                    e.printStackTrace();  
	                } finally {  
	                    client.close();  
	                    System.out.println("S: Done.");  
	                }  
	  
	            }  
	  
	        } catch (Exception e) {  
	  
	            System.out.println("S: Error");  
	  
	            e.printStackTrace();  
	  
	        }  
		}
    }
  
    private String intToIp(int i) {     

        return (i & 0xFF ) + "." +     

      ((i >> 8 ) & 0xFF) + "." +     

      ((i >> 16 ) & 0xFF) + "." +     

      ( i >> 24 & 0xFF) ;

   } 
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		 if(previewrun){
			 aCamera.setPreviewCallback(null);
			 aCamera.stopPreview();
			 previewrun=false;
			 aCamera.release();
			 aCamera=null;
		 }
		 initcamera(holder);
	}
	
	public void initcamera(SurfaceHolder holder){
		try{
			   aCamera=android.hardware.Camera.open();
			   aCamera.setPreviewDisplay(holder);
			  
			   aCamera.setPreviewCallback(null);
		//	   aCamera.setPreviewCallback(mJpegPreviewCallback);
		       Parameters params = aCamera.getParameters();  
		//     params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		       params.setPreviewSize(640,480);  
		      
		       if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {  
                   params.setRotation(90);
               }  
               if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {  
                   params.set("orientation", "landscape");  
                   params.set("rotation", 90);  
               }  
	            
		       aCamera.setParameters(params);  
		       aCamera.startPreview();  
		       previewrun=true;
			   
			}catch(Exception e){
				
			}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		initcamera(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		 if(aCamera != null) {
			 aCamera.setPreviewCallback(null);
			 aCamera.stopPreview();
			 previewrun=false;
			 aCamera.release();
			 aCamera=null;
		 }
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP||keyCode==KeyEvent.KEYCODE_VOLUME_DOWN) {
			if(aCamera!=null){
				aCamera.setPreviewCallback(mJpegPreviewCallback);
				Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
	    	  	vibrator.vibrate(1000);
	    	  	inum=0;
	    	  	deletedata=true;
	    	  	textcontent.setText("READY FOR CHECK!");
 			}else{
 				initcamera(mSurfaceHolder);
 				aCamera.setPreviewCallback(mJpegPreviewCallback);
 				Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
	    	  	vibrator.vibrate(1000);
	    	  	inum=0;
	    	  	deletedata=true;
	    	  	textcontent.setText("READY FOR CHECK!");
 			}
		}
			return super.onKeyDown(keyCode, event);
	}

}
