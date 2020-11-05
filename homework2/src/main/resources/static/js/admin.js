$(function(){
	$('#table_id_example').DataTable({
	     ajax:{
	    	 url : 'admin/books',
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
			 "render" : function(data, type, row, meta) {
					return '<button type="button" class="btn btn-primary btn-sm" onclick="deleteBook(\''
							+ row.id
							+ '\')">下架</button>&nbsp<button type="button" class="btn btn-primary btn-sm" onclick="editBook(\''
							+ row.id + '\')">修改</button>';
				}
			 
		 }]
		
	});
});

deleteBook = function(id){
	$.ajax({
		method : "DELETE",
		url : "admin/book",
		data : {
			"bookId" : id
		}
	}).done(function(){
		alert("下架成功");
		$('#table_id_example').DataTable().ajax.reload();
	})
}

editBook = function(id){
	$.ajax({
		method : "GET",
		url : "admin/book",
		data : {
			"bookId" : id
		}
	}).done(function(data){
		if(data == null || data == ""){
			alert("未找到相关书籍");
		}
		$("#idforedit").val(data.id)
		$("#bookNameforedit").val(data.bookName);
		$("#pressforedit").val(data.press);
		$("#authorforedit").val(data.author);
		$("#numberforedit").val(data.number);
		$("#status").val(data.status);
        $('#editModal').modal('show');
	})
}

$("#editBook").click(function(){
	if($("#bookNameforedit").val().trim() == "" || $("#pressforedit").val().trim() == "" || $("#authorforedit").val().trim() == "" || $("#numberforedit").val().trim() == "")
		alert("请输入正确的数据格式");
	$.ajax({
		method : "PUT",
		url : "admin/book",
        contentType : 'application/json;charset=UTF-8',
		data : JSON.stringify({
			"id" : $("#idforedit").val(),
			"bookName" : $("#bookNameforedit").val(),
			"press" : $("#pressforedit").val(),
			"author" : $("#authorforedit").val(),
			"number" : $("#numberforedit").val(),
			"status" : $('#status').val()
		})
	}).done(function(){
		//$("#editModal").hide();
        $('#editModal').modal('hide');
		$('#table_id_example').DataTable().ajax.reload();
	})
});

$("#addBook").click(function(){
	if($("#bookName").val().trim() == "" || $("#press").val().trim() == "" || $("#author").val().trim() == "" || $("#number").val().trim() == "")
		alert("请输入正确的数据格式");
	$.ajax({
		method : "POST",
		url : "admin/book",
		dataType : "json",
        contentType : 'application/json;charset=UTF-8',
		data : JSON.stringify({
			"bookName" : $("#bookName").val(),
			"press" : $("#press").val(),
			"author" : $("#author").val(),
			"number" : $("#number").val()
		})
	}).done(function(){
        $('#myModal').modal('hide');
		$('#table_id_example').DataTable().ajax.reload();
	})
});

