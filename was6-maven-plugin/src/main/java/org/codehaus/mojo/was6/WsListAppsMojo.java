package org.codehaus.mojo.was6;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.settings.Server;
import org.dom4j.Document;

/**
 * Lists all the applications installed on a WebSphere Server or Cell. This goal is a wrapper for the AdminApp.list()
 * command of the wsadmin tool. Refer to the wsadmin documentation for information on this operation.
 * 
 * @goal wsListApps
 * @author <a href="mailto:david@codehaus.org">David J. M. Karlsen</a>
 * @author <a href="mailto:javier.murciego@gmail.com">Javier Murciego</a>
 */
public class WsListAppsMojo
    extends AbstractWas6Mojo
{
    /**
     * properties file containing attributes to set in the JVM System properties.
     * 
     * @parameter expression="${was6.properties}"
     */
    private File properties;

    /**
     * A script file to be executed before the main command or file.
     * 
     * @parameter expression="${was6.profile}"
     */
    private File profile;

    /**
     * The name of the server profile to be used.
     * 
     * @parameter expression="${was6.profileName}"
     */
    private String profileName;

    /**
     * Valid values are SOAP, RMI, JMS, and NONE. NONE means that no server connection is made.
     * 
     * @parameter expression="${was6.conntype}"
     */
    private String conntype;

    /**
     * The host attribute is optional and only specified if the conntype is specified. It contains the hostname of the
     * machine to connect to.
     * 
     * @parameter expression="${was6.host}"
     */
    private String host;

    /**
     * Only needed if the conntype is specified. It contains the port on the host to connect to.
     * This will also be used as the keys to lookup userId/password from settings.xml, if not
     * specified in the username and/or password parameter in the plugin.
     * 
     * @parameter expression="${was6.port}"
     */
    private Integer port;

    /**
     * The user ID to authenticate with.
     * This takes precedence over values defined in settings.xml.
     * 
     * @parameter expression="${was6.user}"
     */
    private String user;

    /**
     * The password to authenticate with.
     * This takes precedence over values defined in settings.xml.
     * 
     * @parameter expression="${was6.password}"
     */
    private String password;

    /**
     * {@inheritDoc}
     */
    protected void configureBuildScript( Document document )
        throws MojoExecutionException
    {
        super.configureTaskAttribute( document, "properties", properties );
        super.configureTaskAttribute( document, "profile", profile );
        super.configureTaskAttribute( document, "profileName", profileName );
        super.configureTaskAttribute( document, "host", host );
        super.configureTaskAttribute( document, "port", port );
        super.configureTaskAttribute( document, "conntype", conntype );
        
        Server settingsServer = getSettings().getServer( host );
        
        String userToUse = user != null ? user : settingsServer != null ? settingsServer.getUsername() : "";
        super.configureTaskAttribute( document, "user", userToUse );
        
        String passwordToUse = password != null ? password : settingsServer != null ? settingsServer.getPassword() : "";
        super.configureTaskAttribute( document, "password", passwordToUse );
    }

    /**
     * {@inheritDoc}
     */
    protected String getTaskName()
    {
        return "wsListApps";
    }

}
