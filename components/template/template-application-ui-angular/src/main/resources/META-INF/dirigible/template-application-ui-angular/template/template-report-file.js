/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
import * as reportFileTemplate from "template-application-ui-angular/template/ui/reportFile";
import * as generateUtils from "service-generate/template/generateUtils";
import * as parameterUtils from "service-generate/template/parameterUtils";

export function generate(model, parameters) {
    model = JSON.parse(model);
    let templateSources = getTemplate(parameters).sources;
    model?.columns?.forEach(e => {
        const parsedDataType = parameterUtils.parseDataTypes(e.type);
        e.typeJava = parsedDataType.java;
        e.typeTypescript = parsedDataType.ts;
        if (e.typeTypescript === "Date") {
            model.hasDates = true
        }
    });
    model?.parameters?.forEach(e => {
        const parsedDataType = parameterUtils.parseDataTypes(e.type);
        e.typeJava = parsedDataType.java;
        e.typeTypescript = parsedDataType.ts;
        model?.conditions?.forEach(c => {
            if (c.right === `:${e.name}` && e.typeTypescript === 'string' && c.operation === 'LIKE') {
                e.isLikeCondition = true;
            }
        });
    });
    model.queryLines = model.query.split("\n");
    if (parameters.extensionPoint === undefined) {
        parameters.extensionPoint = parameters.projectName;
    }
    if (parameters.name === undefined) {
        parameters.name = model.name;
    }
    if (parameters.perspectiveName === undefined) {
        parameters.perspectiveName = model.name;
    }

    parameters.roles = [];

    if (model.security.generateDefaultRoles === "true") {
        const roleData = {}
        roleData["entityName"] = model.name;

        if (model.security.roleRead && model.security.roleRead != "") {
            roleData["roleRead"] = model.security.roleRead;
        }

        if (model.security.roleWrite && model.security.roleWrite != "") {
            roleData["roleWrite"] = model.security.roleWrite;
        }

        parameters.roles.push(roleData);
    }

    return generateUtils.generateGeneric(model, parameters, templateSources);
};

export function getTemplate(parameters) {
    return {
        name: "Application Report - Table",
        description: "Application Table Report",
        extension: "report",
        sources: reportFileTemplate.getSources(),
        parameters: [
            {
                name: "extensionPoint",
                label: "Extension Point",
                placeholder: "Enter Extension Point, if not provided defaults to the project name",
                required: false
            },
            {
                name: "brand",
                label: "Brand",
                placeholder: "Enter Brand"
            },
            {
                name: "brandUrl",
                label: "Brand URL",
                placeholder: "Enter Brand URL"
            },
            {
                name: "title",
                label: "Title",
                placeholder: "Enter Title"
            },
            {
                name: "navigationOrder",
                label: "Navigation Order",
                placeholder: "Enter Navigation Order Number"
            }]
    };
};