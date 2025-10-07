$(document).ready(function () {
    loadTable();
});

function loadTable() {
    if ($.fn.DataTable.isDataTable('#departments')) {
        $('#departments').DataTable().destroy();
    }
    $('#departments').DataTable({
        ajax: {
            url: '/api/departments',
            dataSrc: 'data'
        },
        columns: [
            { data: 'deptNo' },
            { data: 'deptName' },
            {
                data: 'deptNo', render: function (data) {
                    return '<button onclick="edit(' + data + ')">Edit</button> <button onclick="del(' + data + ')">Delete</button>'
                }
            }
        ]
    });
}


function create() {
    $('#formTitle').text('Create Department');
    $('#deptNo').val('');
    $('#deptName').val('');
    $('#formArea').show();
}

function edit(id) {
    fetch('/api/departments/' + id)
        .then(r => r.json())
        .then(res => {
            const d = res.data;
            $('#formTitle').text('Edit Department');
            $('#deptNo').val(d.deptNo).prop('disabled', true);
            $('#deptName').val(d.deptName);
            $('#formArea').show();
        });
}

function closeForm() {
    $('#formArea').hide();
    $('#deptNo').prop('disabled', false);
}

function save() {
    const deptNo = $('#deptNo').val().trim();
    const deptName = $('#deptName').val().trim();
    if (!deptNo || !deptName) { alert('All fields required'); return; }

    const payload = { deptNo, deptName };
    const isEdit = $('#deptNo').prop('disabled');
    const method = isEdit ? 'PUT' : 'POST';
    const url = isEdit ? '/api/departments/' + deptNo : '/api/departments';

    fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(r => {
        if (r.ok) {
            closeForm();
            loadTable();
        } else {
            r.text().then(t => alert('Error: ' + t));
        }
    });
}

function del(id) {
    if (confirm('Delete department ' + id + '?')) {
        fetch('/api/departments/' + id, { method: 'DELETE' })
            .then(r => {
                if (r.status === 204) loadTable();
                else r.text().then(t => alert('Error: ' + t));
            });
    }
}
