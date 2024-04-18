/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
const daoTemplateManager = dirigibleRequire("template-application-dao/template/template");
const generateUtils = dirigibleRequire("ide-generate-service/template/generateUtils");
const parameterUtils = dirigibleRequire("ide-generate-service/template/parameterUtils");

exports.generate = function (model, parameters) {
    model = JSON.parse(model).model;
    const templateSources = exports.getTemplate(parameters).sources;
    parameterUtils.process(model, parameters)
    return generateUtils.generateFiles(model, parameters, templateSources);
};

exports.getTemplate = function (parameters) {
    const daoTemplate = daoTemplateManager.getTemplate(parameters);

    let templateSources = [{
        location: "/template-application-rest/api/utils/HttpUtils.ts.template",
        action: "copy",
        rename: "gen/api/utils/HttpUtils.ts",
    }, {
        location: "/template-application-rest/api/utils/ForbiddenError.ts.template",
        action: "copy",
        rename: "gen/api/utils/ForbiddenError.ts",
    }, {
        location: "/template-application-rest/api/utils/ValidationError.ts.template",
        action: "copy",
        rename: "gen/api/utils/ValidationError.ts",
    }, {
        location: "/template-application-rest/api/api.openapi.template",
        action: "generate",
        rename: "gen/{{fileName}}.openapi",
        engine: "velocity"
    }, {
        location: "/template-application-rest/api/entity.ts.template",
        action: "generate",
        rename: "gen/api/{{perspectiveName}}/{{name}}Service.ts",
        engine: "velocity",
        collection: "apiModels"
    }, {
        location: "/template-application-rest/api/reportEntity.ts.template",
        action: "generate",
        rename: "gen/api/{{perspectiveName}}/{{name}}Service.ts",
        engine: "velocity",
        collection: "reportModels"
    }, {
        location: "/template-application-rest/project.json.mjs",
        action: "generate",
        rename: "project.json",
        engine: "javascript"
    }, {
        location: "/template-application-rest/tsconfig.json.template",
        action: "generate",
        rename: "tsconfig.json",
        engine: "velocity"
    }];
    templateSources = templateSources.concat(daoTemplate.sources);

    let templateParameters = [];
    templateParameters = templateParameters.concat(daoTemplate.parameters);
    templateParameters.push

    templateParameters.push({
        name: "addOpenApiInfo",
        label: "OpenAPI Info (Optional)",
        type: "checkbox",
    });
    templateParameters.push({
        name: "openApiTitle",
        label: "OpenAPI Title",
        placeholder: "Enter API title, if not provided the application title is taken",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiVersion",
        label: "OpenAPI Version",
        placeholder: "Enter API version, default value is 1.0.0",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiDescription",
        label: "OpenAPI Description",
        placeholder: "Enter API description, if not provided the application description is taken",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiTermsOfService",
        label: "OpenAPI Terms Of Service",
        placeholder: "Enter API terms of service",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiContactEmail",
        label: "OpenAPI Contact Email",
        placeholder: "Enter API contact email",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiContactUrl",
        label: "OpenAPI Contact URL",
        placeholder: "Enter API contact URL",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiLicenseName",
        label: "OpenAPI License Name",
        placeholder: "Enter API license name",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiLicenseUrl",
        label: "OpenAPI License URL",
        placeholder: "Enter API license URL",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiExternalDocsDescription",
        label: "OpenAPI External Docs Description",
        placeholder: "Enter API external docs description",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });
    templateParameters.push({
        name: "openApiExternalDocsUrl",
        label: "OpenAPI External Docs URL",
        placeholder: "Enter API external docs URL",
        required: false,
        ui: {
            hide: {
                property: "addOpenApiInfo",
                value: false
            }
        }
    });

    return {
        name: "Application - REST",
        description: "Application with REST APIs",
        extension: "model",
        sources: templateSources,
        parameters: templateParameters
    };
};