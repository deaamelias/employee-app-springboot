$(document).ready(loadTable);
function loadTable(){
    if($.fn.DataTable.isDataTable('#dept_manager'))$('#dept_manager').DataTable().destroy();
    $('#dept_manager').DataTable({
        ajax:{url:'/api/department-managers',dataSrc:'data'},
        columns:[
            {data:'deptNo'},{data:'empNo'},{data:'fromDate'},{data:'toDate'},
            {data:null,render:d=>`
        <button onclick="edit('${d.deptNo}','${d.empNo}')">Edit</button>
        <button onclick="del('${d.deptNo}','${d.empNo}')">Delete</button>`}
        ]
    });
}
function create(){
    $('#formTitle').text('Add Manager');
    $('#deptNo,#empNo,#fromDate,#toDate').val('').prop('disabled',false);
    $('#formArea').show();
}
function edit(deptNo,empNo){
    fetch(`/api/department-managers/${deptNo}/${empNo}`).then(r=>r.json()).then(res=>{
        const d=res.data;
        $('#formTitle').text('Edit Manager');
        $('#deptNo').val(d.deptNo).prop('disabled',true);
        $('#empNo').val(d.empNo).prop('disabled',true);
        $('#fromDate').val(d.fromDate);
        $('#toDate').val(d.toDate);
        $('#formArea').show();
    });
}
function save(){
    const deptNo=$('#deptNo').val(),empNo=$('#empNo').val();
    const body={deptNo,empNo,fromDate:$('#fromDate').val(),toDate:$('#toDate').val()};
    const edit=$('#empNo').prop('disabled');
    const method=edit?'PUT':'POST';
    const url=edit?`/api/department-managers/${deptNo}/${empNo}`:'/api/department-managers';
    fetch(url,{method,headers:{'Content-Type':'application/json'},body:JSON.stringify(body)})
        .then(r=>{if(r.ok){closeForm();loadTable();}else r.text().then(alert);});
}
function del(deptNo,empNo){
    if(confirm(`Delete manager ${empNo}?`))
        fetch(`/api/department-managers/${deptNo}/${empNo}`,{method:'DELETE'}).then(r=>{if(r.ok)loadTable();else r.text().then(alert);});
}
function closeForm(){$('#formArea').hide();}
