$(function () {
    function resetAccess() {
        $(".btn-delete-access").click(function (event) {
            $(event.target).parent().parent("tr").remove();
        });
    }

    resetAccess();

    $(".btn-add-access").click(function (event) {
        var element = $(event.target);
        var result = window.prompt(element.attr("data-prompt"));

        if (result == null) {
            return;
        }

        var row = $("<tr>");
        $(element.attr("data-target")).append(row);

        var lastColumn = $("<td>");
        lastColumn.text(result);
        row.append(lastColumn);

        lastColumn = $("<td>" + $(".template-select").html() + "</td>").insertAfter(lastColumn);
        lastColumn = $("<td>" + $(".template-delete").html() + "</td>").insertAfter(lastColumn);

        resetAccess();
    });

    $(".btn-save-access").click(function () {
        var form = $('<form method="post">');

        function getAccessOf(element, prefix)
        {
            element.find("tr").each(function (_, row) {
                row = $(row);

                var input = $("<input>");
                input.attr("type", "hidden");
                input.attr("name", [prefix + row.find("td").get(0).innerText]);
                switch (row.find("td").get(1).children[0].value) {
                    case "User": input.attr("value", "USER_ACCESS"); break;
                    case "Admin": input.attr("value", "ADMIN_ACCESS"); break;
                }

                form.append(input);
            });
        }

        var input = $("<input>");
        input.attr("type", "hidden");
        input.attr("name", "_csrf");
        input.attr("value", $.cookie("XSRF-TOKEN"));
        form.append(input);

        getAccessOf($(".table-access-org"), "org_");
        getAccessOf($(".table-access-users"), "user_");

        $("body").append(form);
        form.submit();
    });
});