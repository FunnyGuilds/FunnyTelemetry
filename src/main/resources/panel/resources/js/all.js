$(function () {
    $(".logout-button").click(function () {
        $(".logout-form").submit();
    });

    $(".hidden-ip").click(function (event) {
        var target = $(event.target);

        if (target.attr("data-hidden") !== "0") {
            target.attr("data-text", target.text());
            target.text(target.attr("data-ip"));
            target.attr("data-hidden", "0");
        } else {
            target.text(target.attr("data-text"));
            target.attr("data-text", null);
            target.attr("data-hidden", "1");
        }
    });

    $(".toggle-ips").click(function () {
        $(".hidden-ip").click();
    });

    var urlParams = new URLSearchParams(window.location.search);
    // noinspection EqualityComparisonWithCoercionJS
    var size = urlParams.get("size") == undefined ? 10 : parseInt(urlParams.get("size"));

    var paginationSelect = $(".pagination-select > select");
    paginationSelect.val(size);

    paginationSelect.change(function (event) {
        var params = "";

        urlParams.forEach(function (value, key) {
            if (key !== "size") {
                params += "&" + key + "=" + value;
            }
        });

        window.location.href = "?size=" + $(event.target).val() + params;
    });

    $(".confirmation-link").click(function (event) {
        if (!confirm($(event.target).attr('data-confirmation'))) {
            event.preventDefault();
        }
    })
});