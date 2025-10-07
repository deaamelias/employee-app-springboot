$(document).ready(loadTable);
function loadTable(){
    if($.fn.DataTable.isDataTable('#titles'))$('#titles').DataTable().destroy();
    $('#titles').DataTable({
        ajax:{url:'/api/titles',dataSrc:'data'},
        columns:[
            {data:'empNo'},{data:'title'},{data:'fromDate'},{data:'toDate'},
            {data:'empNo',render:id=>`<button onclick="edit('${id}')">Edit</button><button onclick="del('${id}')">Delete</button>`}
        ]
    });
}
function create(){
    $('#formTitle').text('Add Title');
    $('#empNo,#title,#fromDate,#toDate').val('').prop('disabled',false);
    $('#formArea').show();
}
function edit(id){
    fetch('/api/titles/'+id).then(r=>r.json()).then(res=>{
        const d=res.data;
        $('#formTitle').text('Edit Title');
        $('#empNo').val(d.empNo).prop('disabled',true);
        $('#title').val(d.title);
        $('#fromDate').val(d.fromDate);
        $('#toDate').val(d.toDate);
        $('#formArea').show();
    });
}
function save(){
    const empNo=$('#empNo').val();
    const body={empNo,title:$('#title').val(),fromDate:$('#fromDate').val(),toDate:$('#toDate').val()};
    const edit=$('#empNo').prop('disabled');
    const method=edit?'PUT':'POST';
    const url=edit?`/api/titles/${empNo}`:'/api/titles';
    fetch(url,{method,headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
        .then(r=>{if(r.ok){closeForm();loadTable();}else r.text().then(alert);});
}
function del(id){if(confirm('Delete title?'))fetch('/api/titles/'+id,{method:'DELETE'}).then(r=>{if(r.ok)loadTable();else r.text().then(alert);});}
function closeForm(){$('#formArea').hide();}
