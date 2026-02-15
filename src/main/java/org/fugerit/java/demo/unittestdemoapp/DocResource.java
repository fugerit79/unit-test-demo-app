package org.fugerit.java.demo.unittestdemoapp;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.process.DocProcessContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Slf4j
@ApplicationScoped
@Path("/doc")
@SecurityScheme(securitySchemeName = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "JWT Bearer Token Authentication")
public class DocResource {

    DocHelper docHelper;

    public DocResource(DocHelper docHelper) {
        this.docHelper = docHelper;
    }

    @APIResponse(responseCode = "200", description = "The HTML document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Operation(operationId = "HTMLExample", summary = "Versione HTML del documento (ruoli: admin, user)", description = "Generato con Fugerti Venus Doc https://venusdocs.fugerit.org/")
    @GET
    @Produces("text/html")
    @Path("/example.html")
    @SecurityRequirement(name = "bearerAuth")
    @RolesAllowed({ "admin", "user" })
    public Response htmlExample() throws IOException {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_HTML)).build();
    }

    @APIResponse(responseCode = "200", description = "The Markdown document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Operation(operationId = "MarkdownExample", summary = "Versione MarkDown del documento (ruoli: admin, user, guest)", description = "Generato con Fugerti Venus Doc https://venusdocs.fugerit.org/")
    @GET
    @Produces("text/markdown")
    @Path("/example.md")
    @SecurityRequirement(name = "bearerAuth")
    @RolesAllowed({ "admin", "user", "guest" })
    public Response markdownExample() throws IOException {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_MD)).build();
    }

    @APIResponse(responseCode = "200", description = "The AsciiDoc document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Operation(operationId = "AsciiDocExample", summary = "Versione AsciiDoc del documento (ruoli: admin)", description = "Generato con Fugerti Venus Doc https://venusdocs.fugerit.org/")
    @GET
    @Produces("text/asciidoc")
    @Path("/example.adoc")
    @SecurityRequirement(name = "bearerAuth")
    @RolesAllowed("admin")
    public Response asciidocExample() throws IOException {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_ADOC)).build();
    }

    @APIResponse(responseCode = "200", description = "The PDF document content")
    @APIResponse(responseCode = "500", description = "In case of an unexpected error")
    @Tag(name = "document")
    @Operation(operationId = "PDFExample", summary = "Versione AsciiDoc del documento (ruoli: admin)", description = "Generato con Fugerti Venus Doc https://venusdocs.fugerit.org/")
    @GET
    @Produces("application/pdf")
    @Path("/example.pdf")
    @RolesAllowed("admin")
    public Response pdfExample() throws IOException {
        return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_PDF)).build();
    }

    /*
     * metodo worker che genera effettivamente i documenti tramite il framework :
     * https://github.com/fugerit-org/fj-doc ( documentazione : https://venusdocs.fugerit.org/ )
     */
    byte[] processDocument(String handlerId) throws IOException {
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
        }
    }

}
