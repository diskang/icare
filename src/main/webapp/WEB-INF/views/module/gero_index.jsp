<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>黄浦区养老院主页</title>
<link href="/static/css/index.css" rel="stylesheet" type="text/css">
<link href="/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/static/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<!----------------------banner------------------------------>
<link href="static/css/lrtk.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/static/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/static/js/koala.min.1.5.js"></script>
<script type="text/javascript" src="/static/js/login.js"></script>
<!--------------下拉---------------->
<script  type="text/javascript" src="/static/js/bootstrap.min.js"></script>
</head>
<body>

<!-------------------------头部---------------------------->
<div class="head">
  <div class="container header-s">
    <div class="logo"><img src="/static/images/LOGO.png" ></div>
    <div class="dianhua"><img src="/static/images/d_1.png"></div>
  </div>
</div>
<!--------------------------导航条-------------------------------->
<div class="daohang" >
  <div class="container Navigation">
    <ul>
      <li class="nav"><a href="#" >网站首页</a></li>
      <li class="nav-b"><a href="#intro"  >养老院简介</a></li>
      <li class="nav-b"><a href="#service"   >服务内容</a></li>
      <li class="nav-b"><a href="#environment"  >养老院环境</a></li>
      <li class="nav-b"><a href="#connect"  >联系我们</a></li>
    </ul>
  </div>
  <div class="btn-group pull-right">
    <a id="login" class="btn" href="#"> 登录 </a>
  </div>
</div>

<div class="banner">
  <div id="fsD1" class="focus">  
    <div id="D1pic1" class="fPic">  
        <div class="fcon" style="display: none;">
            <a   href="#"><img src="/static/images/01.jpg" style="opacity: 1; "></a>  
        </div>
        <div class="fcon" style="display: none;">
            <a   href="#"><img src="/static/images/02.jpg" style="opacity: 1; "></a>
        </div>
        <div class="fcon" style="display: none;">
            <a   href="#"><img src="/static/images/03.jpg" style="opacity: 1; "></a>
        </div>
        <div class="fcon" style="display: none;">
            <a   href="#"><img src="/static/images/04.jpg" style="opacity: 1; "></a>
        </div>    
    </div>
    <div class="fbg">  
      <div class="D1fBt" id="D1fBt">  
        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>1</i></a>  
        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>2</i></a>  
        <a href="javascript:void(0)" hidefocus="true" target="_self" class="current"><i>3</i></a>  
        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>4</i></a>  
      </div>  
    </div>    
  </div>
  <script type="text/javascript">
	  Qfast.add('widgets', { path: "static/js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	  Qfast(false, 'widgets', function () {
		  K.tabs({
			  id: 'fsD1',   //焦点图包裹id  
			  conId: "D1pic1",  //** 大图域包裹id  
			  tabId:"D1fBt",  
			  tabTn:"a",
			  conCn: '.fcon', //** 大图域配置class       
			  auto: 1,   //自动播放 1或0
			  effect: 'fade',   //效果配置
			  eType: 'click', //** 鼠标事件
			  pageBt:true,//是否有按钮切换页码
			  bns: ['.prev', '.next'],//** 前后按钮配置class                          
			  interval: 3000  //** 停顿时间  
		 }) 
	 })  
  </script>
</div>

<!--------------------------------养老院简介---------------->
<div class="introduction" id="intro">
  <div class="container">
    <div class="Introduction-a"><img src="/static/images/j_1.png"></div>
    <div class="house"> 
      <span class="span1"><img src="/static/images/t_1.jpg" ></span> 
      <span class="span2">HouseCare养老院是养老院是一家融老人休养、医疗护理和文化娱乐为一体的新型医护型养老院。养老位于上海市普陀区中心，占地面积5900余平方米，设置床位459张。养老院毗邻上海火车站和中远两湾城，轨交、公交等便利的交通及院内智能停车系统为探视老人提供极大方便。 <br/>
      HouseCare养老院既为自理老人提供环境优雅温馨的养老养生居所，又为因年迈或疾病困扰而不能自理老人提供精心的生活照料和医疗、康复护理服务，特别是对失能老人的照料护理具有显著特点。老人居室分为二人、三人、四人、多人间不同户型，结合老人需求及不同身体状况提供不同选择。房间内中央空调、电视、电话、冰箱、家具、独立卫生沐浴间等生活设施一应俱全。新风系统、监控系统、呼叫系统、BA系统、红外布防、公共广播、老人定位（GPS）系统等现代化设施为老人安全提供保障。 </span>
    </div>
  </div>
</div>

<!--------------------------------服务管理---------------->
<div class="di" id="service">
  <div class="container treatment">
    <div class="about"> 
      <span class="span1">服务内容</span> 
      <span class="span2"><img src="/static/images/d_3.png"></span>
    </div>
    <div class="treatment-a">
      <ul>
        <li>
          <span class="span1"><img src="/static/picture/tt_1.jpg"></span>
          <span class="span2">
            <h4>员工管理</h4>
            员工基本信息的管理系统。协助养老院管理护工，医生，以及入住老人的详细信息。
          </span> 
          <span class="span3">View details>></span>
        </li>
        <li>
          <span class="span1"><img src="/static/picture/tt_2.jpg"></span>
          <span class="span2">
            <h4>护理服务</h4>
            记录老人的服务信息。帮助护工记录给老人的服务，提高护工的工作效率，改善对老人的服务质量。
          </span> 
          <span class="span3">View details>></span>
        </li>
        <li>
          <span class="span1"><img src="/static/picture/tt_3.jpg"></span>
          <span class="span2">
            <h4>员工管理</h4>
            分析老人的健康感情状况。利用人工智能设备，机器学习理论知识，分析老人的健康感情状况，及时与老人进行情感交流。
          </span> 
          <span class="span3">View details>></span>
        </li>
      </ul>
    </div>
  </div>
</div>

<!--------------------------------养老院环境---------------->
<div class="di2" id='environment'>
  <div class="Environment container">
    <div class="Introduction-a"><img src="/static/images/j_2.png"></div>
    <div class="Environment-a">
      <h4>养老院房间环境</h4>
      <h4>养老周边环境</h4>
    </div>
    <div class="Environment-b">
      <ul>
        <li class="huan-a"><span class="span1"><img src="/static/picture/p_1.jpg" ></span> <span class="span2"><img src="/static/picture/p_2.jpg"></span></li>
        <li class="huan-b"><span class="span1"><img src="/static/picture/p_3.jpg"></span> <span class="span2"><img src="/static/picture/p_4.jpg"></span></li>
        <li class="huan-c"><span class="span1"><img src="/static/picture/p_5.jpg"></span> <span class="span2"><img src="/static/picture/p_6.jpg"></span></li>
      </ul>
    </div>
  </div>
</div>
<!---------------------------------------联系我们------------------------>
<div class="di-a" id="connect">
  <div class="container us">
    <div class="about"> <span class="span1">联系我们</span> <span class="span2"><img src="/static/images/d_3.png"></span></div>
    <div class="contact">
      <div class="contact-a">
        <div class="con-a">在线留言</div>
        <div class="con-b">
          <ul>
            <li class="conli">
              <text>姓名</text>
              <span>
              <input class="form-control2" type="text" placeholder="用户名" name="username">
              </span>
              <text class="text1">必填</text>
            </li>
            <li class="conli">
              <text>电话</text>
              <span>
              <input class="form-control2" type="text" placeholder="用户名" name="username">
              </span>
              <text class="text1">必填</text>
            </li>
            <li class="conli">
              <text>QQ</text>
              <span>
              <input class="form-control2" type="text" placeholder="用户名" name="username">
              </span>
              <text class="text1">必填</text>
            </li>
            <li class="conli">
              <text>留言</text>
              <span>
              <input class="form-control-a" type="text" placeholder="用户名" name="username">
              </span></li>
            <li class="conli-a">
              <span class="span1">
              <button class="btnn btn-success" >提交</button>
              </span>
              <span class="span2">
              <button class="btnn btn-info" >重置</button>
              </span></li>
          </ul>
        </div>
      </div>
      <div class="contact-b">
        <div class="cont-a"><img src="/static/images/tel_1.png"></div>
        <div class="cont-b">
          <ul>
            <li>
              <span class="span1"><img src="/static/images/f_1.png" ></span>
              <span class="span2">网络在线咨询<br/>马上提问<br/>即时回答</span>
            </li>
            <li>
              <span class="span1"><img src="/static/images/f_2.png" ></span>
              <span class="span2">网络在线咨询<br/>马上提问<br/>即时回答</span>
            </li>
            <li>
              <span class="span1"><img src="/static/images/f_3.png" ></span>
              <span class="span2">网络在线咨询<br/>马上提问<br/>即时回答</span>
            </li>
            <li>
              <span class="span1"><img src="/static/images/f_4.png" ></span>
              <span class="span2">网络在线咨询<br/>马上提问<br/>即时回答</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<!----------------------------------------------------底部-->
<div class="fotter container">
  <div class="fotter-a"><img src="/static/images/LOGO.png" width="239" height="58"></div>
  <div class="fotter-b">地址：上海市普陀区江宁路1207室18楼  电话：021-61838550   服务热线：400－0088－559<br/>  Copyright 2012 (C) HouseCare连锁养老康复机构 版权所有<br/>沪ICP证060399号　沪公网安备110312202036号
  </div>
</div>


<!-- 背景遮盖层 -->  
<div id="dialog-form" style="display:none;z-index：1005；">
    <form id="form1" name="form1" method="post" target="_self" action="login">
      <div align="center" class="STYLE1">
        <p></p>
        <p>
          <span>
            <span>用户名：</span>
            <input type="text" name="username" />
          </span>
        </p>
        <p> </p>
        <p>
          <span>密   码：</span>
          <input type="password" name="userpwd" />
        </p>
        <p> </p>
        <p>
          <input name="Submit" type="submit"  value="登  录"/>
          <input type="button" style="margin-left:8px;" onClick='$("#dialog-form").dialog("close");' value="取  消" />
        </p>
      </div>
    </form>
</div>  

</body>
</html>
