$(document).ready(function () {
    loadTable();
});

function loadTable() {
    if ($.fn.DataTable.isDataTable('#employees')) {
        $('#employees').DataTable().destroy();
    }
    $('#employees').DataTable({
        ajax: {
            url: '/api/employees',
            dataSrc: 'data'
        },
        columns: [
            {data: 'empNo'},
            {data: 'firstName'},
            {data: 'lastName'},
            {data: 'gender'},
            {data: 'birthDate'},
            {data: 'hireDate'},
            {
                data: 'empNo', render: function (data) {
                    return '<button onclick="edit(' + data + ')">Edit</button> <button onclick="del(' + data + ')">Delete</button>'
                }
            }
        ]
    });
}

function create() {
    $('#formTitle').text('Create');
    $('#empNo').val('');
    $('#firstName').val('');
    $('#lastName').val('');
    $('#gender').val('M');
    $('#birthDate').val('');
    $('#hireDate').val('');
    $('#formArea').show();
}

function edit(id) {
    fetch('/api/employees/' + id)
        .then(r => r.json())
        .then(res => {
            const d = res.data;
            $('#formTitle').text('Edit ' + id);
            $('#empNo').val(d.empNo);
            $('#firstName').val(d.firstName);
            $('#lastName').val(d.lastName);
            $('#gender').val(d.gender);
            $('#birthDate').val(d.birthDate);
            $('#hireDate').val(d.hireDate);
            $('#formArea').show();
        });
}

function closeForm() {
    $('#formArea').hide();
}

function save() {
    const id = $('#empNo').val();
    const firstName = $('#firstName').val().trim();
    const lastName = $('#lastName').val().trim();
    const birthDate = $('#birthDate').val();

    if (!firstName) { alert('First name is required'); return; }
    if (!lastName) { alert('Last name is required'); return; }
    if (!birthDate) { alert('Birth date is required'); return; }

    const tableData = $('#employees').DataTable().rows().data().toArray();
    const duplicate = tableData.some(emp =>
        emp.firstName.toLowerCase() === firstName.toLowerCase() &&
        emp.lastName.toLowerCase() === lastName.toLowerCase() &&
        emp.birthDate === birthDate &&
        emp.empNo != id
    );
    if (duplicate) {
        alert('Employee already exists!');
        return;
    }

    const payload = { firstName, lastName, gender: $('#gender').val(), birthDate, hireDate: $('#hireDate').val() };
    let method = id ? 'PUT' : 'POST';
    let url = '/api/employees' + (id ? '/' + id : '');

    fetch(url, {method, headers:{'Content-Type':'application/json'}, body: JSON.stringify(payload)})
        .then(r => {
            if(r.ok){ closeForm(); loadTable(); }
            else r.text().then(t => alert('Error: ' + t));
        });
}


function del(id) {
    if (confirm('Delete employee ' + id + '?')) {
        fetch('/api/employees/' + id, {method: 'DELETE'}).then(r => {
            if (r.status === 204) loadTable(); else r.text().then(t => alert('Error: ' + t));
        });
    }
}