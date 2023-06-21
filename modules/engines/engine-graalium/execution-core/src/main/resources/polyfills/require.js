/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
const DirigibleSourceProvider = Java.type("org.eclipse.dirigible.graalium.core.modules.DirigibleSourceProvider");
const dirigibleSourceProvider = new DirigibleSourceProvider();

const _loadedModules = {};

function _require (path) {
    let moduleInfo = _loadedModules[path];
    if (moduleInfo) {
        return moduleInfo;
    }

    const code = '(function(exports, module, require) { ' + dirigibleSourceProvider.getSource(path) + '\n})';
    moduleInfo = {
        loaded: false,
        id: path,
        exports: {},
        require: require
    };
    _loadedModules[path] = moduleInfo;

    const compiledWrapper = load({
        name: path,
        script: code
    });
    const cjsModuleProps = [
        moduleInfo.exports, /* exports */
        moduleInfo, /* module */
        moduleInfo.require /* require */
    ];

    compiledWrapper.apply(moduleInfo.exports, cjsModuleProps);
    moduleInfo.loaded = true;
    return moduleInfo;
};

function require(path) {
    const module = _require(path);
    return module.exports;
}

globalThis.require = require;
globalThis.dirigibleRequire = globalThis.require;