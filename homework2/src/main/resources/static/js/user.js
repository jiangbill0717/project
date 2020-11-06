$(function(){
	$('#table_id_example').DataTable({
	     ajax:{
	    	 url : 'user/books',
	    	 dataSrc : ""
	     },//对象数据地址,
	     destroy:true,
	     columns:[
	    	 {"data":"id"},
		     {"data":"bookName"},
		     {"data":"press"},
		     {"data":"author"},
		     {"data":"number"},
		     {"data":"status"},
		     {"data":"null"}
		     ],
		 columnDefs : [{
			 "targets" : 5,
			 "render" : function(data, type, row, meta){
				 if(data == 1) return '正常';
				 if(data == 2) return '已借完';
				 if(data == 3) return '已下架'
			 }
			 
		 },{
			 "targets" : 6,
			 "render" : function(data, type, row, meta){
				 if(row.number <= 0){
					return '<button type="button" class="btn btn-primary btn-sm" disabled onclick="borrow(\''+ row.id+'\')">借阅</button>';
				 }
				 return '<button type="button" class="btn btn-primary btn-sm" onclick="borrow(\''+ row.id+'\')">借阅</button>';
			 }
			 
		 }]
		
	});
});

borrow=function(bookId){
	$.ajax({
		method : "GET",
		url : "user/borrow",
		data : {
			"bookId" : bookId
		}
	}).done(function(){
		alert("借阅成功");
		$('#table_id_example').DataTable().ajax.reload();
	})
}