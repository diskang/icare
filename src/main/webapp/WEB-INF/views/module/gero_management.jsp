<%@page import="com.sjtu.icare.modules.sys.utils.UserUtils"%>
<%@page import="com.sjtu.icare.modules.sys.entity.User"%>
<%@page import="com.sjtu.icare.modules.sys.utils.security.SystemAuthorizingRealm.UserPrincipal"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="org.apache.shiro.subject.Subject"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="utf-8">

<title>resthouse管理界面</title>

<link href="/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/static/css/manager.css" rel="stylesheet" type="text/css">
<link href="/static/css/conter.css" rel="stylesheet" type="text/css">
<link href="/static/css/jeesite.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/static/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/static/css/easyui.css">
<link rel="stylesheet" type="text/css" href="/static/css/icon.css">
<link rel="stylesheet" type="text/css" href="/static/css/bootstrap-datetimepicker.min.css">
<link href="/static/css/vis.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%
  User user = UserUtils.getUser();
  String username = user.getUsername();
  int geroId = user.getGeroId();
  int userType = user.getUserType();
  int userId = user.getUserId();
  int id = user.getId();
%>
<script language="JavaScript"> 
  function getUser(){ 
     var user = new Object();
     user.id = <%=id%>;
     user.gero_id = <%=geroId %>;
     user.user_type = <%=userType %>;
     user.user_id = <%=userId %>;
     return user;
  }
</script> 
<!-------------------------头部-------------------------->
<div class="head">
  <div class="container header-s">
    <div class="logo"><img src="/static/images/logo.png" ></div>
  </div>
</div>
<!--------------------------导航条-------------------------------->
<div class="daohang">
  <div class="container Navigation">
    <ul id="topNavi">
    </ul>
    <div class="btn-group pull-right">
      <a id="logout" class="btn" href="/admin/logout"> 退出 </a>
    </div>
    <div class="btn-group pull-right">
      <a id="userinfo" class="btn"onclick="user.drawinfo()"> 个人中心 </a>
    </div>
    <text id="welcome" class="pull-right" ></text>
  </div>
</div>


<div class="di">
  <div class="container Information">
  <!--------------------------左导航--------------------------------> 
    <div class="leftNav" id="leftNavi" >
      <div style="background:url('/static/images/r_1.jpg');line-height:30px;font-size:16px;color: #fff;height:30px;padding-left:20px;font-size:20px;margin:0;text-align:left;">菜单</div>
      <ul id="lefttree" style="padding-left:20px;height:670px;padding-top:10px;text-align:left;overflow:scroll;">
      </ul>
    </div>


  <!--------------------------右内容-------------------------------->
    <div class="rightNav" id="rightNavi" style="min-height:710px;">
    

    <!---------------------------用户信息-------------------------->
    <div id="usershow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">个人信息中心</div>
        <div class="old">
          <div class="page-header">基本信息</div>
          <div id="user-Info-card-a" class="info-card-a" >
            <table >
              <tr><td class="td1"><text>用户名: </text></td><td class="td3"><div id="uusername" ></div></td></tr>
              <tr><td class="td1"><text>姓名: </text></td><td class="td3"><div id="uname" ></div></td></tr>
              <tr><td class="td1"><text>密码: </text></td><td class="td3"><a id="userinfo" class="btn"onclick="user.changepwd()"> 修改</a></td></tr>
            </table>
          </div>
        </div>
      </div>

      <!--------------------------老人列表-------------------------------->
      <div id="eldershow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">老人信息中心</div>
        <div class="old">
          <div class="page-header">老人信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">姓名:</label>
              <div class="col-smm-2">
                <input id="elder_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="control" for="name">房间:</label>
              <div class="col-smm-2">
                <input id="elder_areafullname" type='button' onclick='elder.searcharea_id();' class="form-control"  value=""></input>
                <input id="elder_areaid"  class="form-control hide"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="control" for="name">护理等级:</label>
              <div class="col-smm-2">
                <select id="elder_care_level" class="form-control"  value="">
                  <option value=''></option>
                  <option value='0'>专护</option>
                  <option value='1'>1级</option>
                  <option value='2'>2级</option>
                  <option value='3'>3级</option>
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-reset" class="btn btn-default"  style='float:right;'onclick="elder.reset()"  >重置</button>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" style='float:right;'onclick="elder.doSearch()"  >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px;width:100%">
              <table id="elderpage"  class="easyui-datagrid" title="老人信息列表" style="height:400px;" data-options="onDblClickRow:elder.onElderDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'elder_id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'area_fullname',width:150,align:'center'">房 间</th>
                    <th data-options="field:'name',width:110,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:200,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:100,align:'center'">性 别</th>
                    <th data-options="field:'phone_no',width:120,align:'center'">电话</th>
                    <th data-options="field:'care_level',width:100,align:'center'">护理等级</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------家属列表-------------------------------->
      <div id="relativeshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">家属信息中心</div>
        <div class="old">
          <div class="page-header">家属信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">姓名:</label>
              <div class="col-smm-2">
                <input id="relative_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="control" for="name">相关老人:</label>
              <div class="col-smm-2">
                <input id="relative_eldername" type='button' onclick='relative.searchelder_id();' class="form-control"  value=""></input>
                <input id="relative_elderid" class="form-control hide"  value=""></input>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button  class="btn btn-default" onclick="relative.reset()" style="margin-left:30px;" >重置</button>
                <button id="relative-search" class="btn btn-default" onclick="relative.doSearch()"  >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px;width:100%">
              <table id="relativepage"  class="easyui-datagrid" title="家属信息列表" style="height:400px;" data-options="onDblClickRow:relative.onRelativeDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'relative_id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:200,align:'center'">姓 名</th>
                    <th data-options="field:'gender',width:160,align:'center'">性 别</th>
                    <th data-options="field:'phone_no',width:250,align:'center'">电话</th>
                    <th data-options="field:'elder_name',width:200,align:'center'">相关老人</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>





      <!--------------------------员工列表-------------------------------->
      <div id="staffshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工信息中心</div>
        <div class="old">
          <div class="page-header">员工信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control">姓名:</label>
              <div class="col-smm-2">
                <input id="staff_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="control">身份证:</label>
              <div class="col-smm-2">
                <input id="staff_identity_no" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="control">角色:</label>
              <div class="col-smm-2">
                <input id="staff_role" class="form-control"  value="">
                </input>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="staff-reset" class="btn btn-default"  style='float:right;' onclick="staff.reset()"  >重置</button>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="staff-search" class="btn btn-default" style='float:right;' onclick="staff.doSearch()">搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px;width:100%">
              <table id="staffpage"  class="easyui-datagrid" title="员工信息列表" style="height:400px;" data-options="onDblClickRow:staff.onStaffDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'staff_id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'role',width:150,align:'center'">角色</th>
                    <th data-options="field:'name',width:100,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:200,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:90,align:'center'">性 别</th>
                    <th data-options="field:'phone_no',width:120,align:'center'">电 话</th>
                    <th data-options="field:'email',width:130,align:'center'">邮 箱</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------区域管理-------------------------------->
      <div id="areashow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">区域管理</div>
        <div class="old">
          <div class="page-header"></div>
          <div id="area-Info-card-a" class="info-card-a">
          <div  id="areapanel" class="easyui-panel" title="区域列表" style="height:500px;width:500px;padding:10px;margin:0;overflow:scroll;" data-options="
            width:500,
            tools: [{ 
                iconCls: 'icon-add', 
                handler: function() { 
                  area.addTreenode();
                } 
            }, '-',{ 
                iconCls: 'icon-remove', 
                handler: function(){ 
                  area.delTreenode();
                }
            }]">
              <ul id="areatree" style="padding-left:20px;text-align:left;">
              </ul>
          </div>
          </div>
          <div class="info-card-b">
            <div id="areaInfo" class="hide">
              <table>
                <tr><td style='padding-right:10px'>名称: </td><td><input type="text" id="aname" ></input></td></tr>
                <tr><td >类型: </td><td><input type="text" id="atype"></input></td></tr>
                <tr><td >级别: </td><td><input type="text" id="alevel"></input></td></tr>
              </table>
              <div class="col-md-offset-2">
                <button id="areabutton" class="btn btn-default" onclick="area.buttonclk()" style="margin-left:180px;margin-top:10px;" >确定</button>
              </div>
            </div>
          </div>
        </div>
      </div>



      <!--------------------------排班-------------------------------->
      <div id="arrangeshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工排班</div>
        <div class="old">
          <div class="page-header">查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">角色:</label>
              <div class="col-smm-2">
                <select id="arrange_role" class="form-control">
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" onclick="arrange.drawArrangeList2()" style="margin-left:30px;" >搜索</button>
              </div>
            </div>
          </div>
          <div id="calendar" class="fc fc-ltr fc-unthemed">
            <div class="fc-toolbar">
              <div class="fc-left">
                <div class="fc-button-group">
                  <button type="button" class="fc-prev-button fc-state-default"><span class="fc-icon fc-icon-left-single-arrow"></span></button>
                  <button type="button" class="fc-next-button fc-state-default"><span class="fc-icon fc-icon-right-single-arrow"></span></button>
                </div>
                <button type="button" class="fc-today-button fc-state-default ">今天</button>
              </div>
              <div class="fc-center"><h2>员工排班记录</h2></div>
              <div class="fc-right">
                <button type="button" id="button-allow" class="fc-allow-button fc-state-default1 fc-state-default2">排班</button>
                <button type="button" class="fc-submit-button fc-state-default ">提交</button>
              </div>
            </div>
            <div class="fc-view-container">
              <div class="fc-view" >
                <table style="width:800px;">
                  <thead >
                    <tr>
                      <th class="fc-name" >姓名</th>
                      <th class="fc-sun"></th>
                      <th class="fc-mon"></th>
                      <th class="fc-tue"></th>
                      <th class="fc-wed"></th>
                      <th class="fc-thu"></th>
                      <th class="fc-fri"></th>
                      <th class="fc-sat"></th>
                    </tr>
                  </thead>
                </table>
              </div>
              <div class="fc-view" style="max-height:470px;width:826px;overflow-y:scroll;" >
                <table>
                  <tbody class="fc-body">
                  </tbody>
                </table>  
              </div>
            </div>
          </div>
        </div>
      </div>


      <!--------------------------专护职责-------------------------------->
      <div id="eldercareshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">专护职责情况</div>
        <div class="old">
          <div class="page-header"></div>
          <div id="careitemvision"></div>
          <div class="itemcont">
            <div class="itemleft">
              <div class="dutyhead">专护人员</div>
              <ul id="eldercarercont" class="easyui-tree" data-options="
                onSelect:function(){
                var node = $('#eldercarercont').tree('getSelected');
                  eldercare.getList(node.id,node.text);
              }">
              </ul>
            </div>
            <div id="elderdutypanel" class="itemmiddle hide">
              <div class="dutyhead">控制操作</div>
              <div class="dutycontrol">
                <div></div>
                <table>
                  <tr></tr>
                  <tr> <td>结束日期:</td>
                    <td>
                    <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                      <input class="form-control" size="16" id="eldercarer-end" type="text" value="" readonly>
                      <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                    </div>
                    </td>
                  </tr>
                  <tr></tr>
                </table>
                <div><button id="eldercarerbutton" class="btn btn-default" onclick="eldercare.buttonclk()" style="margin-left:180px;margin-top:10px;" >提交</button>
                </div>
                <p>说明: 结束日期为真正结束日期的后一天。</p>
              </div>
            </div>
            <div class="itemright">
              <div class="dutyhead">老人</div>
              <ul id="elderchecktree" class="easyui-tree" data-options="checkbox:true">
              </ul>
            </div>
          </div>
          
        </div>
      </div>



      <!--------------------------房护职责-------------------------------->
      <div id="areacareshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">房护职责情况</div>
        <div class="old">
          <div class="page-header"></div>
          <div id="areaitemvision"></div>
          <div class="itemcont">
            <div class="itemleft">
              <div class="dutyhead">房护人员</div>
              <ul id="areacarercont" class="easyui-tree"data-options="
                onSelect:function(){
                var node = $('#areacarercont').tree('getSelected');
                  areacare.getList(node.id,node.text);
              }">
              </ul>
            </div>
            <div id="areadutypanel" class="itemmiddle hide">
              <div class="dutyhead">控制操作</div>
              <div class="dutycontrol">
                <table>
                  <tr></tr>
                  <tr> <td>结束日期:</td>
                    <td>
                    <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                      <input class="form-control" size="16" id="areacarer-end" type="text" value="" readonly>
                      <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                    </div>
                    </td>
                  </tr>
                  <tr></tr>
                </table>
                <div><button id="areacarerbutton" class="btn btn-default" onclick="areacare.buttonclk()" style="margin-left:180px;margin-top:10px;" >提交</button>
                </div>
                <p>说明: 结束日期为真正结束日期的后一天。</p>
              </div>
            </div>
            <div class="itemright">
              <div class="dutyhead">区域</div>
              <ul id="areachecktree" class="easyui-tree" data-options="checkbox:true,onlyLeafCheck:true">
              </ul>
            </div>
          </div>
          
        </div>
      </div>



      <!--------------------------项目列表-------------------------------->
      <div id="itemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">项目总表</div>
        <div class="old">
          <div class="page-header"></div>
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="careitempage"  class="easyui-datagrid" title="专护项目列表" style="height:270px;" data-options="onDblClickRow:item.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'care_level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
          <div class="list" style="min-height:300px">
              <table id="areaitempage"  class="easyui-datagrid" title="房护项目列表" style="height:270px;" data-options="onDblClickRow:item.onAreaDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'notes',width:400,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------院方项目列表-------------------------------->
      <div id="gerocareitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院项目管理</div>
        <div class="old">
          <div class="page-header"></div>
          <div class="list" style="min-height:500px;padding-top:10px;width:100%">
              <table id="gerocareitempage"  class="easyui-datagrid" title="专护项目列表" style="height:270px;" data-options="onDblClickRow:geroItem.onGCIDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'period',width:80,align:'center'">周期</th>
                    <th data-options="field:'frequency',width:80,align:'center'">频率</th>
                    <th data-options="field:'start_time',width:100,align:'center'">开始时间</th>
                    <th data-options="field:'end_time',width:100,align:'center'">完成时间</th>
                    <th data-options="field:'notes',width:200,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>
      <div id="geroareaitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院项目管理</div>
        <div class="old">
          <div class="page-header"></div>
          <div class="list" style="min-height:500px;padding-top:10px;width:100%">
              <table id="geroareaitempage"  class="easyui-datagrid" title="房护项目列表" style="height:270px;" data-options="onDblClickRow:geroItem.onGAIDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:180,align:'center'">名 称</th>
                    <th data-options="field:'period',width:120,align:'center'">周期</th>
                    <th data-options="field:'frequency',width:120,align:'center'">频率</th>
                    <th data-options="field:'notes',width:390,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------老人专属项目列表-------------------------------->
      <div id="eldercareitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">老人专属项目查询</div>
        <div class="old">
          <div class="page-header">老人选择:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">老人:</label>
              <div class="col-smm-2">
                <input id="care_item_elder_name" type:"button" onclick="care_item.chooseelder()" class="form-control"  value=""></input>
                <input id="care_item_elder_id" class="hide" value=""></input>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px;width:100%">
              <table id="eldercareitempage"  class="easyui-datagrid" title="老人专属项目列表" style="height:400px;" data-options="onDblClickRow:care_item.onDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'care_item_id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'icon',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'care_item_name',width:150,align:'center'">项目名</th>
                    <th data-options="field:'period',width:120,align:'center'">周期</th>
                    <th data-options="field:'level',width:120,align:'center'">护理等级</th>
                    <th data-options="field:'start_time',width:200,align:'center'">开始时间</th>
                    <th data-options="field:'end_time',width:200,align:'center'">完成时间</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------院方角色列表-------------------------------->
      <div id="geroroleshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院角色管理</div>
        <div class="old">
          <div class="page-header"></div>
          <div class="list" style="min-height:500px;padding-top:10px;width:100%">
              <table id="gerorolepage"  class="easyui-datagrid" title="角色列表" style="height:300px;width:500px;" data-options="onDblClickRow:role.onRoleDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:300,align:'center'">角色名</th>
                    <th data-options="field:'notes',width:510,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------权限列表-------------------------------->
      <div id="authorityshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">权限中心</div>
        <div class="old">
          <div class="page-header"></div>
          <div id="role-Info-card-a" class="info-card-a">
          <div  id="authoritypanel" class="easyui-panel" title="权限列表" style="height:500px;width:500px;padding:10px;margin:0;overflow:scroll;" data-options="
            width:500,
            tools: [{ 
                iconCls: 'icon-add', 
                handler: function() { 
                  authority.addTreenode();
                } 
            }, '-',{ 
                iconCls: 'icon-remove', 
                handler: function(){ 
                  authority.delTreenode();
                }
            }]">
              <ul id="authoritytree" style="padding-left:20px;text-align:left;">
              </ul>
          </div>
          </div>
          <div class="info-card-b">
            <div id="authorityInfo" class="hide">
              <table>
                <tr><td>name: </td><td><input type="text" id="pname" ></input></td></tr>
                <tr><td>permission: </td><td><input type="text" id="ppermission"></input></td></tr>
                <tr><td>href: </td><td><input type="text" id="phref"></input></td></tr>
                <tr><td>icon: </td><td><input type="text" id="picon"></input></td></tr>
                <tr><td>api: </td><td><input type="text" id="papi"></input></td></tr>
                <tr><td>notes: </td><td><input type="text" id="pnotes"></input></td></tr>
              </table>
              <div class="col-md-offset-2">
                <button id="authoritybutton" class="btn btn-default" onclick="authority.buttonclk()" style="margin-left:180px;margin-top:10px;" >确定</button>
              </div>
            </div>
          </div>
        </div>
      </div>


    </div><!--rightNav-->
  </div><!--inforamtion-->
</div><!--di-->

<!-----------------------老人个人信息------------------------------>
<div id="elder-dialog-form"  class="easyui-dialog" title="老人信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
          if(elder.method!=='post')
            elder.editElderInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if((elder.method==='put' && elder.flag)|| elder.method==='post'  )
            {
              elder.buttonclk();
            }
            else $('#elder-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="elder-Info-card" class="info-card">
      <div id="elder-Info-card-a" class="info-card-a">
        <text id="epnote" style="font-size:17px;color:#f00;" class='hide'>*项为必填项,不能为空</text>
        <table>
          <tr><td class="td1"><text>*老人姓名： </text></td><td class="td2"><input id="ename"class="easyui-validatebox textbox equalwidth" required='required' missingMessage="不能为空"></input></td></tr>
          <tr><td class="td1"><text>*出生日期：</text></td><td class="td2">
              <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                <input class="form-control easyui-validatebox textbox" size="16" id="ebirthday" type="text" required='required' value="" readonly />
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
              </div></td></tr>
          <tr><td class="td1"><text>*电    话： </text></td><td class="td2"><input id="ephone_no" class="easyui-validatebox textbox equalwidth" data-options="required:true,validType:'phoneNum'"></input></td></tr>
          <tr><td class="td1"><text>性    别：</text></td><td class="td2"><input type="radio" name="egender" value='0' />男 <input type="radio" name="egender" value='1' />女</td></tr>
          <tr><td class="td1"><text>*身份证号：</text></td><td class="td2"><input id="eidentity_no"class="easyui-validatebox textbox equalwidth" data-options="required:true,invalidMessage:'123',validType:'idcard'"></input></td></tr>
          <tr><td class="td1"><text>*入住床号：</text></td><td class="td2"><input id="earea_fullname" onclick="elder.area_idclick();" class="easyui-validatebox textbox equalwidth" type="button" data-options="required:true"></input>
            <input id="earea_id" class="hide"></input>
          </td></tr>
          <tr><td class="td1"><text>婚姻状况：</text></td><td class="td2"><select id="emarriage" class='equalwidth'>
            <option value=''></option>
            <option value='0'>未婚</option>
            <option value='1'>已婚</option>
            <option value='2'>离异</option>
            <option value='3'>丧偶</option>
          </select></td></tr>
          <tr><td class="td1"><text>民    族：</text></td><td class="td2"><input id="enationality" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>籍    贯：</text></td><td class="td2"><input id="enative_place" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>护理等级：</text></td><td class="td2"><select id="ecare_level" class='equalwidth'>
            <option value=''></option>
            <option value='0'>专护</option>
            <option value='1'>1级</option>
            <option value='2'>2级</option>
            <option value='3'>3级</option>
          </select></td></tr>
          <tr><td class="td1"><text>户口所在地：</text></td><td class="td2"><input id="eresidence" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>政治面貌：</text></td><td class="td2"><input id="epolitical_status" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>教育水平：</text></td><td class="td2"><select id="eeducation" class='equalwidth'>
            <option value=''></option>
            <option value='初中'>初中</option>
            <option value='中专'>中专</option>
            <option value='高中'>高中</option>
            <option value='技校'>技校</option>
            <option value='大专'>大专</option>
            <option value='本科及以'>本科及以上</option>
          </select></td></tr>
          <tr><td class="td1"><text>社保卡号：</text></td><td class="td2"><input id="enssf_id" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>档案编号：</text></td><td class="td2"><input id="earchive_id" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>家庭地址：</text></td><td class="td2"><input id="eaddress" class='equalwidth'></input></td></tr>
          <tr><td class="td1"><text>入院日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control"  class='equalwidth' size="16" id="echeckin_date" type="text" value="" readonly>
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div></td></tr>
          <tr><td class="td1"><text>离院日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" class='equalwidth'size="16" id="echeckout_date" type="text" value="" readonly>
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div></input></td></tr>
        </table>
      </div>
      <div id="elder-Info-card-b" class="info-card-b"><img src="images/p_2.jpg" onclick="if(elder.method==='put') photo.doit(rhurl.root+'/uploadObject/user'+elder.uid);">
        <div id="epic" class="hide">请点击图片进行上传</div>
      </div>
    </div>
</div>


<!-----------------------家属个人信息------------------------------>
<div id="relative-dialog-form"  class="easyui-dialog" title="家属信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
            if(relative.method!=='post')
            relative.editRelativeInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if ((relative.method==='put' && relative.flag)|| relative.method==='post'  )
            {
              relative.buttonclk();
            }
            else $('#relative-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="relative-Info-card" class="info-card ">
      <div id="relative-Info-card-a" class="info-card-a">
        <text id="rpnote" style="font-size:17px;color:#f00;" class='hide'>*项为必填项,不能为空</text>
        <table>
          <tr><td class="td1"><text>*姓    名： </text></td><td class="td2"><input id="rname"class="easyui-validatebox textbox equalwidth" data-options="required:true"></input></td></tr>
          <tr><td class="td1"><text>*电    话： </text></td><td class="td2"><input id="rphone_no" class="easyui-validatebox textbox equalwidth" data-options="required:true,invalidMessage:'123',validType:'phoneNum'"></input></td></tr>
          <tr><td class="td1"><text>性    别：</text></td><td class="td2"><input type="radio" name="rgender" value='0' checked="checked" />男 <input type="radio" name="rgender" value='1' />女</td></tr>
          <tr><td class="td1"><text>*老人：</text></td><td class="td2"><input id="relder_name" onclick="relative.elder_idclick();" class="easyui-validatebox textbox equalwidth"  type="button" data-options="required:true"></input><input id="relder_id" class="hide"  ></input></td></tr>
          <tr><td class="td1"><text>*出生日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control easyui-validatebox textbox" data-options="required:true" size="16" id="rbirthday" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
          <tr><td class="td1"><text>*身份证号：</text></td><td class="td2"><input id="ridentity_no"class="easyui-validatebox textbox equalwidth" data-options="required:true,invalidMessage:'123',validType:'idcard'"></input></td></tr>
          <tr><td class="td1"><text>联系人： </text></td><td class="td2"><select id="rurgent"  class="equalwidth">
            <option value=""></option>
            <option value="0">第一联系人</option>
            <option value="1">第二联系人</option>
            <option value="2">第三联系人</option>
          </select></td></tr>
          <tr><td class="td1"><text>与老人关系：</text></td><td class="td2"><input id="rrelationship" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>婚姻状况：</text></td><td class="td2"><select id="rmarriage" class="equalwidth">
            <option value=''></option>
            <option value='0'>未婚</option>
            <option value='1'>已婚</option>
            <option value='2'>离异</option>
            <option value='3'>丧偶</option>
          </select></td></tr>
          <tr><td class="td1"><text>民    族：</text></td><td class="td2"><input id="rnationality" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>政治面貌：</text></td><td class="td2"><input id="rpolitical_status" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>电子邮箱：</text></td><td class="td2"><input id="remail" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>教育程度：</text></td><td class="td2"><select id="reducation" class="equalwidth">
            <option value=''></option>
            <option value='初中'>初中</option>
            <option value='中专'>中专</option>
            <option value='高中'>高中</option>
            <option value='技校'>技校</option>
            <option value='大专'>大专</option>
            <option value='本科及以'>本科及以上</option>
          </select></td></tr>
          <tr><td class="td1"><text>户口所在地：</text></td><td class="td2"><input id="rresidence" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>居住地址：</text></td><td class="td2"><input id="raddress" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>邮编：</text></td><td class="td2"><input id="rzip_code" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>微信号：</text></td><td class="td2"><input id="rwechat_id" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>籍贯：</text></td><td class="td2"><input id="rnative_place" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>注册日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" size="16" id="rregister_date" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
          <tr><td class="td1"><text>注销日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" size="16" id="rcancel_date" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </input></td></tr>
        </table>
      </div>
      <div id="relative-Info-card-b" class="info-card-b"><img src="images/p_2.jpg" onclick="if(relative.method==='put') photo.doit(rhurl.root+'/uploadObject/user'+relative.uid)"><div id="rpic" class="hide">请点击图片进行上传</div></div>
    </div>
</div>






<!-----------------------员工个人信息------------------------------>
<div id="staff-dialog-form"  class="easyui-dialog" title="员工信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
          if(staff.method!=='post')
            staff.editStaffInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if((staff.method==='put' && staff.flag)|| staff.method==='post'  )
            {
              staff.buttonclk();
            }
            else $('#staff-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="staff-Info-card" class="info-card ">
      <div id="staff-Info-card-a" class="info-card-a">
        <text id="spnote" style="font-size:17px;color:#f00;" class='hide'>*项为必填项,不能为空</text>
        <table>
          <tr><td class="td1"><text>*姓    名： </text></td><td class="td2"><input id="sname" class="easyui-validatebox textbox equalwidth" data-options="required:true"></input></td></tr>
          <tr><td class="td1"><text>*电    话： </text></td><td class="td2"><input id="sphone_no" class="easyui-validatebox textbox equalwidth" data-options="required:true,invalidMessage:'123',validType:'phoneNum'"></input></td></tr>
          <tr><td class="td1"><text>性    别：</text></td><td class="td2"><input type="radio" name="sgender" value='0' checked="checked" />男 <input type="radio" name="sgender" value='1' />女</td></tr>
          <tr><td class="td1"><text>*出生日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control easyui-validatebox textbox" size="16" id="sbirthday" data-options="required:true" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
          <tr><td class="td1"><text>*身份证号：</text></td><td class="td2"><input id="sidentity_no"class="easyui-validatebox textbox equalwidth" data-options="required:true,invalidMessage:'123',validType:'idcard'"></input></td></tr>
          <tr><td class="td1"><text>婚姻状况：</text></td><td class="td2"><select id="smarriage" class="equalwidth">
            <option value=''></option>
            <option value='0'>未婚</option>
            <option value='1'>已婚</option>
            <option value='2'>离异</option>
            <option value='3'>丧偶</option>
          </select></td></tr>
          <tr><td class="td1"><text>民族：</text></td><td class="td2"><input id="snationality" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>政治面貌：</text></td><td class="td2"><input id="spolitical_status" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>电子邮箱：</text></td><td class="td2"><input id="semail" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>户口所在地：</text></td><td class="td2"><input id="sresidence_address" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>档案编号：</text></td><td class="td2"><input id="sarchive_id" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>社保卡号：</text></td><td class="td2"><input id="snssf_id" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>居住地址：</text></td><td class="td2"><input id="shousehold_address" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>注册日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" size="16" id="sregister_date" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
          <tr><td class="td1"><text>注销日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" size="16" id="scancel_date" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
          <tr><td class="td1"><text>邮编：</text></td><td class="td2"><input id="szip_code" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>微信号：</text></td><td class="td2"><input id="swechat_id" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>籍贯：</text></td><td class="td2"><input id="snative_place" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>basic_url：</text></td><td class="td2"><input id="sbasic_url" class="equalwidth"></input></td></tr>
          <tr><td class="td1"><text>离职日期：</text></td><td class="td2">
            <div class="input-group date form_date col-md-10" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
              <input class="form-control" size="16" id="sleave_date" type="text" value="" readonly />
              <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
          </td></tr>
        </table>
      </div>
      <div id="staff-Info-card-b" class="info-card-b"><img src="images/p_2.jpg" onclick="if(staff.method==='put') photo.doit(rhurl.root+'/uploadObject/user'+staff.uid)"><div id="spic" class="hide">请点击图片进行上传</div><div id="spic" class="hide">请点击图片进行上传</div></div>
      <div id="staff-Info-card-c" class="info-card-c">
        <text style="font-size:20px;">角色设置</text>
        <ul id="role-check">
        </ul>
      </div>
    </div>
</div>

<!-----------------------角色权限信息------------------------------>
<div id="role-dialog-form"  class="easyui-dialog" title="角色信息" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.buttonclk();
            $('#role-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="authority-Info-card" class="info-card">
      <ul id="authoritychecktree" class="easyui-tree" style="padding-left:20px;text-align:left;" data-options="lines:true,checkbox:true">
      </ul>
    </div>    
</div>
<div id="rolepost-dialog-form"  class="easyui-dialog" title="角色信息" style="width:300px;height:200px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-200)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.postrole();
            $('#rolepost-dialog-form').dialog('close');
          }
        }]
      ">
      <table>
        <tr><td>角色名:</td><td><input type="text" id="rcname" /></td></tr>
        <tr><td>说  明:</td><td><input type="text" id="rcnotes" /></td></tr>
      </table> 
</div>


<!-----------------------选择区域信息------------------------------>
<div id="area-dialog-form"  class="easyui-dialog" title="区域信息" style="width:300px;height:400px;padding:10px;"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-400)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            var node = $('#areachoosetree').tree('getSelected');
            if(node) {$(areavalue).attr('value',node.id);$(areanamevalue).attr('value',node.attributes.fullname);}
            $('#area-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="area-Info-card" class="info-card">
      <ul id="areachoosetree" class="easyui-tree" style="padding-left:20px;text-align:left;" data-options="">
      </ul>
    </div>    
</div>

<!-----------------------修改密码------------------------------>
<div id="pwd-dialog-form"  class="easyui-dialog" title="修改" style="width:300px;height:220px;padding:10px;"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-220)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            user.putpwd();
          }
        }]
      ">
    <div id="pwd-Info-card" class="info-card">
      <table>
        <tr><td>原密码: </td><td><input type="password" id="old_pwd" /></td></tr>
        <tr><td>新密码: </td><td><input type="password" id="new_pwd" /></td></tr>
        <tr><td>重复密码: </td><td><input type="password" id="renew_pwd" /></td></tr>
      </table>
    </div>    
</div>


<!-----------------------选择项目信息------------------------------>
<div id="eldercareitem-dialog-form"  class="easyui-dialog" title="项目信息" style="width:600px;height:600px;padding:10px;"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-600)*0.5,
        top:($(window).height()-600)*0.5,
        draggable:true,
        iconCls: 'icon-blank',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            var node = $('#careitemchoosetree').tree('getSelected');
            if(node) care_item.postitem(node.id);
            $('#eldercareitem-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="eldercareitem-Info-card" class="info-card-d">
      <div class='old' style="padding-top:10px;">
        <div class="pers-s">请选择一个项目</div>
        <ul id="careitemchoosetree" class="easyui-tree" style="padding-left:20px;text-align:left;max-height:400px;overflow-y:scroll;border:1px solid #bcbcbc;" data-options="
          onSelect:function(){
          var node = $('#careitemchoosetree').tree('getSelected');
          $('#cistime').attr('value',node.attributes.start_time);
          $('#cietime').attr('value',node.attributes.end_time);
          $('#cilevel').attr('value',node.attributes.level);
          $('#ciicon').attr('value',node.attributes.icon);
          $('#ciperiod').attr('value',node.attributes.period);
        }
        ">
        </ul>
      </div>
    </div>    
    <div class="info-card-e">
      <table>
        <tr><td>开始时间</td><td><div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
            <input id="cistime" class="form-control" size="16" type="text"  value="" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td></tr>
        <tr><td>完成时间</td><td>
          <div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
            <input id="cietime" class="form-control" size="16" type="text" value="" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td></tr>
        <tr><td>周期：</td><td><input class='equalwidth' type="text" id="ciperiod" /></td></tr>
        <tr><td>护理等级：</td><td><select class='equalwidth' type="text" id="cilevel">
          <option value=''></option>
          <option value='0'>专护</option>
          <option value='1'>1级</option>
          <option value='2'>2级</option>
          <option value='3'>3级</option>
        </select></td></tr>
        <tr><td>图标：</td><td><input class='equalwidth' type="text" id="ciicon" /></td></tr>
      </table>
    </div>   
</div>
<div id="eldercarechange-dialog-form"  class="easyui-dialog" title="修改" style="width:300px;height:300px;padding:10px;"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-300)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            care_item.putitem();
          }
        }]
      ">
    <div class="info-card">
      <table>
        <tr><td>开始时间</td><td><div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
            <input id="cicstime" class="form-control" size="16" type="text"  value="" readonly />
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td></tr>
        <tr><td>完成时间</td><td>
          <div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii" data-link-field="dtp_input3" data-link-format="hh:ii">
            <input id="cicetime" class="form-control" size="16" type="text"  value="" readonly />
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td></tr>
        <tr><td>周期：</td><td><input class='equalwidth'type="text" id="cicperiod" /></td></tr>
        <tr><td>护理等级：</td><td><select class='equalwidth' type="text" id="ciclevel">
          <option value=''></option>
          <option value='0'>专护</option>
          <option value='1'>1级</option>
          <option value='2'>2级</option>
          <option value='3'>3级</option>
        </select></td></tr>
        <tr><td>图标：</td><td><input class='equalwidth'type="text" id="cicicon" /></td></tr>
      </table>
    </div>    
</div>



<!-----------------------选择老人信息------------------------------>
<div id="elderchoose-dialog-form"  class="easyui-dialog" title="老人信息" style="width:300px;height:400px;padding:10px;"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-400)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            var node = $('#elderchoosetree').tree('getSelected');
            if(node) {$(eldervalue).attr('value',node.id);$(eldernamevalue).attr('value',node.text);}
            elderchoosename=node.text;
            $('#elderchoose-dialog-form').dialog('close');
            if(eldernamevalue==='#care_item_elder_name')care_item.doSearch();
          }
        }]
      ">
    <div id="elderchoose-Info-card" class="info-card">
      <ul id="elderchoosetree" class="easyui-tree" style="padding-left:20px;text-align:left;" data-options="">
      </ul>
    </div>    
</div>



<!----上传照片---->
<div id="photosubmit"  class="easyui-dialog" title="上传照片" style="width:300px;height:200px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-200)*0.5,
        draggable:true,
        iconCls: 'icon-edit',
      ">
      <form id="upload-pic-dropzone" class="alert alert-info" style="height:100px;">
        将文件拖拽至此区域进行上传（或点击此区域）
      </form>
      <div id="upload-pic-dropzone-message" class="alert alert-info" role="alert"></div>
</div>





<!-----------------------项目信息------------------------------>
<div id="gerocareitempost-dialog-form"  class="easyui-dialog" title="项目信息" style="width:300px;height:400px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-400)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            geroItem.clickcareitem();
            $('#gerocareitempost-dialog-form').dialog('close');
          }
        }]
      ">
      <table>
        <tr><td>项目名: </td><td><input type="text" class='equalwidth'id="gciname" /></td></tr>
        <tr><td>护理等级: </td><td><select class='equalwidth' type="text" id="gcilevel">
          <option value=''></option>
          <option value='0'>专护</option>
          <option value='1'>1级</option>
          <option value='2'>2级</option>
          <option value='3'>3级</option>
        </select></td></tr>
        <tr><td>周期: </td><td><input class='equalwidth' type="text" id="gciperiod" /></td></tr>
        <tr><td>频率: </td><td><input class='equalwidth' type="text" id="gcifrequency" /></td></tr>
        <tr><td>图标: </td><td><input class='equalwidth' type="text" id="gciicon" /></td></tr>
        <tr><td>开始时间: </td><td>
          <div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii:ss" data-link-field="dtp_input3" data-link-format="hh:ii:ss">
            <input id="gcistart_time" class="form-control" size="16" type="text" value="" readonly />
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td>
        </tr>
        <tr><td>完成时间: </td><td>
          <div class="input-group date form_time col-md-10 equalwidth" data-date="" data-date-format="hh:ii:ss" data-link-field="dtp_input3" data-link-format="hh:ii:ss">
            <input id="gciend_time" class="form-control" size="16" type="text" value="" readonly />
            <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
          </div></td>
        </tr>
        <tr><td>说明: </td><td><input class='equalwidth' type="text" id="gcinotes" /></td></tr>
      </table>
</div>
<div id="geroareaitempost-dialog-form"  class="easyui-dialog" title="项目信息" style="width:300px;height:300px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-300)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            geroItem.clickareaitem();
            $('#geroareaitempost-dialog-form').dialog('close');
          }
        }]
      ">
      <table>
        <tr><td>项目名: </td><td><input type="text" id="gainame" /></td></tr>
        <tr><td>周期: </td><td><input type="text" id="gaiperiod" /></td></tr>
        <tr><td>频率: </td><td><input type="text" id="gaifrequency" /></td></tr>
        <tr><td>图标: </td><td><input type="text" id="gaiicon" /></td></tr>
        <tr><td>说明: </td><td><input type="text" id="gainotes" /></td></tr>
      </table>
</div>

<script type="text/javascript" src="/static/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="/static/js/topleftNavi.js"></script>
<script type="text/javascript" src="/static/js/photo.js"></script>
<script type="text/javascript" src="/static/js/staff.js"></script>
<script type="text/javascript" src="/static/js/elder.js"></script>
<script type="text/javascript" src="/static/js/item.js"></script>
<script type="text/javascript" src="/static/js/geroitem.js"></script>
<script type="text/javascript" src="/static/js/role.js"></script>
<script type="text/javascript" src="/static/js/authority.js"></script>
<script type="text/javascript" src="/static/js/arrange.js"></script>
<script type="text/javascript" src="/static/js/url.js"></script>
<script type="text/javascript" src="/static/js/relative.js"></script>
<script type="text/javascript" src="/static/js/user.js"></script>
<script type="text/javascript" src="/static/js/area.js"></script>
<script type="text/javascript" src="/static/js/areacare.js"></script>
<script type="text/javascript" src="/static/js/eldercare.js"></script>
<script type="text/javascript" src="/static/js/elder_care_item.js"></script>
<script type="text/javascript" src="/static/js/validbox.js"></script>
<script type="text/javascript" src="/static/js/vis-min.js"></script>
<script type="text/javascript" src="/static/js/dropzone.js"></script>
</body>
</html>
