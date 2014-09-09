var currentAppId;
var currentEnvId;
var environments;
var propertySet;
var accessKeys;

function getAppIdAndEnvIdFromHash() {
    var hash = location.hash;
    if (hash.length > 0)
        return hash.slice(1).split(".");
    return null;
}

function loadApps(cb) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/apps",
        success: function(data) {
            console.log(data);
            data.forEach(function(item) {
                $('#apps-dropdown').append("<li><a href=\"#" + item.id + "\">" + item.name + "</a></li>");
            });

            $('#apps-dropdown a').click(function(e) {
                loadApp(e.currentTarget.hash.slice(1));
            });

            $('#apps-dropdown').append("<li class=\"divider\"></li><li><a href=\"#\" data-toggle=\"modal\" data-target=\"#new-app-modal\">New App</a></li>");

            cb();
        }
    });
}

function refreshEnvironments() {
    $('#env-list').html("");
    environments.forEach(function(env) {
        addEnvToNav(env);
    });
    $("#env-list a").on('click', function(e) {
        var envId = $(e.currentTarget).attr("env-id");
        if (envId != null)
            selectEnv(envId);
    });
}

function addEnvToNav(env) {
    $('#env-list').append("<li><a href=\"#" + currentAppId + "." + env.id + "\" env-id=\"" + env.id + "\">" + env.id + "</a></li>");
}

function selectEnv(env) {
    currentEnvId = env;
    $("#env-list li").removeClass("active");
    $("#env-list li a[env-id='" + env + "']").parent().addClass("active");
    loadConfig(currentAppId, currentEnvId);

    console.log("envs=" + environments.length);
    console.log(environments);
    var i;
    for (i = 0; i< environments.length;i++) {
        var environment = environments[i];
        if (environment["id"] == env) {
            console.log("found environment");
            accessKeys = environment.accessKeys;
            console.log(accessKeys);
            renderAppKeys();
            break;
        }
    }
}

function loadApp(appId) {
    currentAppId = appId;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/apps/" + appId ,
        success: function(data) {
            console.log(data);
            $("#app-name").text(data["name"]);
            $("#app-id").text(data["id"]);
            $("#env-list").html("");
            environments = data["environments"];
            refreshEnvironments();

            propertySet = data["propertySet"];
            refreshProperties();

            if (currentEnvId == null && environments.length > 0) {
                currentEnvId = environments[0].id;
                location.hash = "#" + currentAppId + "." + currentEnvId;
            }

            if (currentEnvId != null) {
                selectEnv(currentEnvId);
            }
        }
    });
}

function loadConfig(appId, envId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/configs/" + appId + "/" + envId,
        success: function(data) {
            console.log(data);
            if (propertySet != null) {
                propertySet.forEach(function(propertyDef) {
                    var loadedval = data[propertyDef.name];
                    if (loadedval == null)
                        loadedval = "";
                    $("#prop-" + propertyDef.name).val(loadedval);
                });
            }
        }
    });
}

function saveConfig() {
    var config = {};
    if (propertySet != null) {
        propertySet.forEach(function(propertyDef) {
            var propval = $("#prop-" + propertyDef.name).val();
            console.log(propval);
            config[propertyDef.name] = propval;
        });
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(config),
        url: "/configs/" + currentAppId + "/" + currentEnvId,
        success: function(data) {
            console.log(data);
        }
    });
}

function deleteEnvironment() {
    if (currentAppId != null && currentEnvId != null) {
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: "/apps/" + currentAppId + "/environments/" + currentEnvId,
            success: function(data) {
                alert("Environment deleted");
                currentEnvId = null;
                loadApp(currentAppId);
            }
        });
    }
}

function deleteProperty(propertyName) {
    if (currentAppId != null) {
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            url: "/apps/" + currentAppId + "/properties/" + propertyName,
            success: function(data) {
                alert("Property deleted");
            }
        });
    }
}

function renderProperty(propertyDef) {
    $.get('templates/dashboard-property.mustache', function(template) {
        var html = Mustache.render(template, propertyDef);
        $("#propsForm #properties").append(html);
    });
}

function renderAppKeys() {
    $.get('templates/dashboard-appkey.mustache', function(template) {
        var html = Mustache.render(template, {appkeys:accessKeys});
        $("#appkeys").html(html);
    });
}

function refreshProperties() {
    console.log("refreshing properties");
    $("#propsForm #properties").html("");
    if (propertySet != null) {
        propertySet.forEach(function(prop) {
            renderProperty(prop);
        });
    }
}


$( document ).ready(function() {

    $("#propsForm").on('submit', function(e) {
        console.log("submitted form");
        e.preventDefault();
        saveConfig();
    });

    $("#new-env-modal .save-btn").on('click', function() {
        var envId = $("#new-env-modal #env-id").val();
        var createEnvRequest = {id:envId};
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: "/apps/" + currentAppId + "/environments",
            data: JSON.stringify(createEnvRequest),
            success: function(data) {
                environments.push(createEnvRequest);
                refreshEnvironments();
            }
        });
    });

    $("#delete-env-modal .save-btn").on('click', function() {
        deleteEnvironment();
    });

    $("#delete-property-modal .save-btn").on('click', function() {
        deleteProperty();
    });

    loadApps(function() {
        var appIdAndEnvId = getAppIdAndEnvIdFromHash();
        if (appIdAndEnvId != null) {
            currentAppId = appIdAndEnvId[0];
            if (appIdAndEnvId.length > 1)
                currentEnvId = appIdAndEnvId[1];

            if (currentAppId != null)
                loadApp(currentAppId);
        }
    });

    $("#new-app-modal .save-btn").on('click', function() {
        console.log("create app clicked");
        var appId = $("#new-app-modal #app-id").val();
        var appName = $("#new-app-modal #app-name").val();

        var createAppRequest = {id:appId, name:appName};
        console.log(createAppRequest);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: "/apps",
            data: JSON.stringify(createAppRequest),
            success: function(data) {
                alert("created app");
            }
        });
    });

    $("#new-property-modal .save-btn").on('click', function() {
        console.log("create property clicked");
        var propName = $("#new-property-modal #name").val();
        var desc = $("#new-property-modal #description").val();
        var type = $("#new-property-modal #type").val();
        var required = $("#new-property-modal #is-required").prop('checked');

        var request = {name:propName, description:desc, type:type, required:required};
        console.log(request);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: "/apps/" + currentAppId + "/properties",
            data: JSON.stringify(request),
            success: function(data) {
                if (propertySet === undefined || propertySet == null)
                    propertySet = [];
                propertySet.add(request);
                refreshProperties();
            }
        });
    });

    $("#new-appkey-modal .save-btn").on('click', function() {
        console.log("create property clicked");
        var appKeyName = $("#new-appkey-modal #app-key-name").val();

        var request = {name:appKeyName};
        console.log(request);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: "/apps/" + currentAppId + "/environments/" + currentEnvId + "/accessKeys",
            data: JSON.stringify(request),
            success: function(data) {
                alert("created app key");
            }
        });
    });

    $("#discard-changes-button").on('click', function() {
        loadConfig(currentAppId, currentEnvId);
    });

    console.log($("#new-property-modal"));
    $('#new-property-modal').on('show.bs.modal', function(e) {
        console.log(e);
        var propName = $(e.relatedTarget).data('prop-name');
        console.log("property-name:" + propName);
    });
});