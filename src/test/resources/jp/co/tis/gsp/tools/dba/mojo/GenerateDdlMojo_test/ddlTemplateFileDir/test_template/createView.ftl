CREATE OR REPLACE VIEW <#if view.schema?has_content>${view.schema}.</#if>${view.name}_TEST AS
${view.sql}
