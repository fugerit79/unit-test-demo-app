package org.fugerit.java.demo.unittestdemoapp;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.fugerit.java.demo.unittestdemoapp.auth.AuthRoles;
import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.process.DocProcessContext;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import static org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles.ADMIN;
import static org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles.USER;

@Slf4j
@ApplicationScoped
@Path("/doc")
@SecurityScheme(securitySchemeName = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "JWT Bearer Token Authentication")
public class DocResource {

    @APIResponse(responseCode = "200", description = "The HTML document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Tag(name = "html")
    @Operation(operationId = "HTMLExample", summary = "Example HTML generation", description = "Generates an example HTML document using Fugerit Venus Doc handler")
    @GET
    @Produces("text/html")
    @Path("/example.html")
    @SecurityRequirement(name = "bearerAuth")
    @AuthRoles(roles = { ADMIN, USER })
    public Response htmlExample() {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_HTML)).build();
    }

    @APIResponse(responseCode = "200", description = "The Markdown document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Tag(name = "markdown")
    @Operation(operationId = "MarkdownExample", summary = "Example Markdown generation", description = "Generates an example Markdown document using Fugerit Venus Doc handler")
    @GET
    @Produces("text/markdown")
    @Path("/example.md")
    @SecurityRequirement(name = "bearerAuth")
    @AuthRoles(roles = { ADMIN })
    public Response markdownExample() {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_MD)).build();
    }

    @APIResponse(responseCode = "200", description = "The AsciiDoc document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Tag(name = "asciidoc")
    @Operation(operationId = "AsciiDocExample", summary = "Example AsciiDoc generation", description = "Generates an example AsciiDoc document using Fugerit Venus Doc handler")
    @GET
    @Produces("text/asciidoc")
    @Path("/example.adoc")
    @SecurityRequirement(name = "bearerAuth")
    @AuthRoles(roles = { ADMIN })
    public Response asciidocExample() {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_ADOC)).build();
    }

    @Inject
    DocHelper docHelper;

    public DocResource(DocHelper docHelper) {
        this.docHelper = docHelper;
    }

    byte[] processDocument(String handlerId) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // create custom data for the fremarker template 'document.ftl'
            List<People> listPeople = Arrays.asList(new People("Luthien", "Tinuviel", "Queen"),
                    new People("Thorin", "Oakshield", "King"));

            log.info("processDocument handlerId : {}", handlerId);
            String chainId = "document";
            // output generation
            this.docHelper.getDocProcessConfig().fullProcess(chainId, DocProcessContext.newContext("listPeople", listPeople),
                    handlerId, baos);
            // return the output
            return baos.toByteArray();
        } catch (Exception e) {
            String message = String.format("Error processing %s, error:%s", handlerId, e);
            log.error(message, e);
            throw new WebApplicationException(message, e);
        }
    }

}
