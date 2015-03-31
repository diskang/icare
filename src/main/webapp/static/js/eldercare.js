var eldercare={
	drawElderCareList:function(){
		$(".inf").addClass('hide');
		$("#eldercareshow").removeClass('hide');
		var items = new vis.DataSet([
    		{id: 1, content: 'item 1', start: new Date(),end: new Date(2015, 5, 21)},
  		]);
		var items2 = new vis.DataSet([
    		{id: 3, content: 'item 1', start: new Date(),end: new Date(2015, 6, 21)},
  		]);
  		var min = new Date(); // 1 april
  		var max = new Date(2017,3,1); // 30 april

    var container = document.getElementById('careitemvision');
    var options = {
      editable: true,

      onAdd: function (item, callback) {
      item.content = prompt('Enter text content for new item:', item.content);
      if (item.content != null) {
        callback(item); // send back adjusted new item
      }
      else {
        callback(null); // cancel item creation
      }
      },

    onMove: function (item, callback) {
      if (confirm('Do you really want to move the item to\n' +
          'start: ' + item.start + '\n' +
          'end: ' + item.end + '?')) {
        callback(item); // send back item as confirmation (can be changed)
      }
      else {
        callback(null); // cancel editing item
      }
    },

    onMoving: function (item, callback) {
      if (item.start < min) item.start = min;
      if (item.start > max) item.start = max;
      if (item.end   > max) item.end   = max;

      callback(item); // send back the (possibly) changed item
    },

    onUpdate: function (item, callback) {
      item.content = prompt('Edit items text:', item.content);
      if (item.content != null) {
        callback(item); // send back adjusted item
      }
      else {
        callback(null); // cancel updating the item
      }
    },

    onRemove: function (item, callback) {
      if (confirm('Remove item ' + item.content + '?')) {
        callback(item); // confirm deletion
      }
      else {
        callback(null); // cancel deletion
      }
    }
  };
  timeline = new vis.Timeline(container, [], options);
  timeline.clear({items: true});
  //timeline.setItems(items2);
},
  
}