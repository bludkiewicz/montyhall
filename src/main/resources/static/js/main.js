let table;
$(document).ready(function () {

    const results = $('#results');
    // config datatables
    table = results.DataTable({
        columns: [
            {
                data: function (row, type, set, meta) {
                    return (meta.row + 1);
                }
            },
            {data: 'doorOne'},
            {data: 'doorTwo'},
            {data: 'doorThree'},
            {data: 'originalChoice'},
            {data: 'selectedChoice'},
            {data: 'winner'}
        ],
        ordering: false,
        responsive: true,
        dom: "<'row m-0'<'col-md'tr>>" +
            "<'row m-0'<'col-md'i><'col-md text-center'l><'col-md'p>>"
    });

    // datatables wraps #results table in a #results_wrapper
    // even if #results_wrapper is hidden, #results table will still flash until the table is wrapped
    // so I'm hiding the #results table as well
    // at this point the #results table is wrapped, so it doesn't need to be hidden anymore
    results.show();

    // config form submission
    $("#options").submit(function (e) {

        // overriding default submission
        e.preventDefault();
        // remove any alerts if present
        $('#alert').remove();
        // process submission
        if ($('#method').val() === 'GET') {
            $.ajax({
                url: '/api/play/' + $('#iterations').val() + '/' + $('#switchDoors').val(),
                method: 'GET',
                success: function (response) {
                    processResponse(response);
                },
                error: function (response) {
                    processError(response);
                }
            });
        } else {
            $.ajax({
                url: '/api/play/',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({"iterations": $('#iterations').val(), "switch": $('#switchDoors').val()}),
                success: function (response) {
                    processResponse(response);
                },
                error: function (response) {
                    processError(response);
                }
            });
        }
    });
});

// replace data in table
function processResponse(response) {

    // show win percentage
    $('#win').show();
    // set percentage
    $('#percentage').html((response.wins / response.attempts * 100).toFixed(2) + "%");

    // show result table if present
    if (response.singleResults.length > 0)
        $('#results_wrapper').show();
    else
        $('#results_wrapper').hide();

    // set results
    table.clear();
    table.rows.add(response.singleResults);
    table.draw();
}

// alert!
function processError(response) {

    // remove results
    $('#win').hide();
    $('#results_wrapper').hide();

    // show error
    $('#alertWrapper div').html('<div id="alert" class="alert alert-danger">' + response.responseJSON.message + '</div>');
}