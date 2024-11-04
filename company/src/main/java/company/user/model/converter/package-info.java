/**
 * JSF converters are objects which are used during JSF conversion phases when plain text value assigned to control
 * in the component tree is converted in order to be placed inside the model (nowadays CDI bean property, formerly JSF
 * managed beans). HTTP request are made with plain text, converters job is to translate plain text to object required
 * by the model. Forms in HTML are made with plain text, converters job is also to translate model object into plain
 * text used in form values.
 */
package company.user.model.converter;
