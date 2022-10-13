/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.components.base.artefact.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.dirigible.commons.api.topology.ITopologicallyDepletable;
import org.eclipse.dirigible.commons.api.topology.ITopologicallySortable;
import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.synchronizer.Synchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TopologyWrapper.
 *
 * @param A the generic type
 */
public class TopologyWrapper<A extends Artefact> implements ITopologicallySortable, ITopologicallyDepletable {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TopologyWrapper.class);
	
	/** The artefact. */
	private A artefact;
	
	/** The wrappers. */
	private Map<String, TopologyWrapper<A>> wrappers;
	
	/** The synchronizer. */
	private Synchronizer<Artefact> synchronizer;
	
	/**
	 * Instantiates a new topology wrapper.
	 *
	 * @param artefact the artefact
	 * @param wrappers the wrappers
	 * @param synchronizer the synchronizer
	 */
	public TopologyWrapper(A artefact, Map<String, TopologyWrapper<A>> wrappers, Synchronizer<Artefact> synchronizer) {
		this.artefact = artefact;
		this.wrappers = wrappers;
		this.synchronizer = synchronizer;
		this.wrappers.put(getId(), this);
	}
	
	/**
	 * Gets the artefact.
	 *
	 * @return the artefact
	 */
	public A getArtefact() {
		return artefact;
	}
	
	/**
	 * Gets the synchronizer.
	 *
	 * @return the synchronizer
	 */
	public Synchronizer<Artefact> getSynchronizer() {
		return synchronizer;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.artefact.getKey();
	}

	/**
	 * Gets the dependencies.
	 *
	 * @return the dependencies
	 */
	@Override
	public List<ITopologicallySortable> getDependencies() {
		List<ITopologicallySortable> dependencies = new ArrayList<ITopologicallySortable>();
		if (this.artefact.getDependencies() != null) {
			String[] keys = this.artefact.getDependencies().split(",");
			for (String key : keys) {
				if (!wrappers.containsKey(key)) {
					if (logger.isWarnEnabled()) {logger.warn("Dependency is not present in this cycle: " + key);}
				} else {
					dependencies.add(wrappers.get(key));
				}
			}
		}
		return dependencies;
	}
	
	/**
	 * Complete.
	 *
	 * @param flow the flow
	 * @return true, if successful
	 */
	@Override
	public boolean complete(String flow) {
		return synchronizer.complete((TopologyWrapper<Artefact>) this, flow);
	}
	
	

}
