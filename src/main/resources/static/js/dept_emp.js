$(document).ready(() => {
    loadTable();
    loadEmpDeptOptions();
});

// =====================
// Load DataTable
// =====================
function loadTable() {
    if ($.fn.DataTable.isDataTable('#dept_emp')) $('#dept_emp').DataTable().destroy();
    $('#dept_emp').DataTable({
        ajax: { url: '/api/department-employees', dataSrc: 'data' },
        columns: [
            { data: 'empNo' },
            { data: 'deptNo' },
            { data: 'fromDate' },
            { data: 'toDate' },
            {
                data: null,
                render: d => `
          <button onclick="edit('${d.empNo}','${d.deptNo}')">Edit</button>
          <button onclick="del('${d.empNo}','${d.deptNo}')">Delete</button>`
            }
        ]
    });
}

// =====================
// Load Emp No & Dept No Options
// =====================
function loadEmpDeptOptions() {
    // Employees
    fetch('/api/employees')
        .then(r => r.json())
        .then(res => {
            console.log("Employees API response:", res); // debug
            const data = res.data || res; // fallback jika tidak ada properti data
            const empSelect = $('#empNo');
            empSelect.empty();
            if (Array.isArray(data) && data.length > 0) {
                data.forEach(e => {
                    const empNo = e.empNo || e.id || e.employeeNumber; // fallback property
                    const name = (e.firstName && e.lastName) ? `${e.firstName} ${e.lastName}` : empNo;
                    empSelect.append(`<option value="${empNo}">${empNo} - ${name}</option>`);
                });
            } else {
                empSelect.append('<option value="">No employees found</option>');
            }
        })
        .catch(err => {
            console.error("Error fetching employees:", err);
            $('#empNo').append('<option value="">Error loading employees</option>');
        });

    // Departments
    fetch('/api/departments')
        .then(r => r.json())
        .then(res => {
            console.log("Departments API response:", res); // debug
            const data = res.data || res; // fallback
            const deptSelect = $('#deptNo');
            deptSelect.empty();
            if (Array.isArray(data) && data.length > 0) {
                data.forEach(d => {
                    const deptNo = d.deptNo || d.id || d.departmentNumber; // fallback
                    const name = d.deptName || deptNo;
                    deptSelect.append(`<option value="${deptNo}">${deptNo} - ${name}</option>`);
                });
            } else {
                deptSelect.append('<option value="">No departments found</option>');
            }
        })
        .catch(err => {
            console.error("Error fetching departments:", err);
            $('#deptNo').append('<option value="">Error loading departments</option>');
        });
}

// =====================
// Form actions
// =====================
function create() {
    $('#formTitle').text('Add Department Employee');
    $('#empNo,#deptNo,#fromDate,#toDate').val('');
    $('#empNo,#deptNo').prop('disabled', false);
    $('#formArea').show();
    loadEmpDeptOptions();
}

function edit(empNo, deptNo) {
    fetch(`/api/department-employees/${empNo}/${deptNo}`)
        .then(r => r.json())
        .then(res => {
            const d = res.data || res;
            $('#formTitle').text('Edit Record');
            $('#empNo').val(d.empNo).prop('disabled', true);
            $('#deptNo').val(d.deptNo).prop('disabled', true);
            $('#fromDate').val(d.fromDate);
            $('#toDate').val(d.toDate);
            $('#formArea').show();
        })
        .catch(err => console.error("Error fetching record:", err));
}

function save() {
    const empNo = $('#empNo').val();
    const deptNo = $('#deptNo').val();
    const body = {
        empNo, deptNo,
        fromDate: $('#fromDate').val(),
        toDate: $('#toDate').val()
    };
    const editMode = $('#empNo').prop('disabled');
    const method = editMode ? 'PUT' : 'POST';
    const url = editMode ? `/api/department-employees/${empNo}/${deptNo}` : '/api/department-employees';

    fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    })
        .then(r => {
            if (r.ok) { closeForm(); loadTable(); }
            else r.text().then(alert);
        })
        .catch(err => console.error("Error saving record:", err));
}

function del(empNo, deptNo) {
    if (confirm(`Delete record for emp ${empNo}?`)) {
        fetch(`/api/department-employees/${empNo}/${deptNo}`, { method: 'DELETE' })
            .then(r => { if (r.ok) loadTable(); else r.text().then(alert); })
            .catch(err => console.error("Error deleting record:", err));
    }
}

function closeForm() { $('#formArea').hide(); }
