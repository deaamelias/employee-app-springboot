$(document).ready(loadTable);
function loadTable(){
    if($.fn.DataTable.isDataTable('#salaries'))$('#salaries').DataTable().destroy();
    $('#salaries').DataTable({
        ajax:{url:'/api/salaries',dataSrc:'data'},
        columns:[
            {data:'empNo'},{data:'salary'},{data:'fromDate'},{data:'toDate'},
            {data:'empNo',render:id=>`<button onclick="edit('${id}')">Edit</button><button onclick="del('${id}')">Delete</button>`}
        ]
    });
}
function create(){
    $('#formTitle').text('Add Salary');
    $('#empNo,#salary,#fromDate,#toDate').val('').prop('disabled',false);
    $('#formArea').show();
}
function edit(id){
    fetch('/api/salaries/'+id).then(r=>r.json()).then(res=>{
        const d=res.data;
        $('#formTitle').text('Edit Salary');
        $('#empNo').val(d.empNo).prop('disabled',true);
        $('#salary').val(d.salary);
        $('#fromDate').val(d.fromDate);
        $('#toDate').val(d.toDate);
        $('#formArea').show();
    });
}
function save(){
    const empNo=$('#empNo').val();
    const body={empNo,salary:$('#salary').val(),fromDate:$('#fromDate').val(),toDate:$('#toDate').val()};
    const edit=$('#empNo').prop('disabled');
    const method=edit?'PUT':'POST';
    const url=edit?`/api/salaries/${empNo}`:'/api/salaries';
    fetch(url,{method,headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
        .then(r=>{if(r.ok){closeForm();loadTable();}else r.text().then(alert);});
}
function del(id){if(confirm('Delete salary?'))fetch('/api/salaries/'+id,{method:'DELETE'}).then(r=>{if(r.ok)loadTable();else r.text().then(alert);});}
function closeForm(){$('#formArea').hide();}
