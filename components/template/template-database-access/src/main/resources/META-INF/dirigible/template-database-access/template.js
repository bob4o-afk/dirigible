/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
export function getTemplate() {
	return {
		name: 'Database Access (API)',
		description: 'Database Access Template',
		glyph: 'sap-icon--database',
		sources: [{
			location: '/template-database-access/service.mjs.template',
			action: 'generate',
			rename: '{{fileName}}.mjs'
		}],
		parameters: [],
		order: 21
	};
};
