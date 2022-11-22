import java.text.SimpleDateFormat
// // locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://ubuntu-server:8080/${appName}-0.1"
        grails.serverURL_noAppName = "http://ubuntu-server:8080/"
        grails.serverSecureURL = "https://ubuntu-server:8181/${appName}-0.1" 
        grails.serverSecureURL_noAppName = "https://ubuntu-server:8181/";
        logFileName = "/home/samuelc/tmadb/logs/tmadbApp."+((new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss")).format(Calendar.getInstance().getTime()))+".log"
        // Dojo Plugin Properties
        dojo.optimize.during.build = false;
        dojo.use.customBuild.js = false;
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://www.gpec.ubc.ca:8080/${appName}-0.1"
        grails.serverURL_noAppName = "http://www.gpec.ubc.ca:8080/"
        grails.serverSecureURL = "https://www.gpec.ubc.ca/${appName}-0.1"
        grails.serverSecureURL_noAppName = "https://www.gpec.ubc.ca/";
        logFileName = "/home/gpec/tmadb/logs/tmadbApp."+((new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss")).format(Calendar.getInstance().getTime()))+".log"
        // Dojo Plugin Properties
        dojo.optimize.during.build = true;
        dojo.use.customBuild.js = true;
    }
    test {
        grails.serverURL = "http://ubuntu-server:8080/${appName}-0.1"
        grails.serverSecureURL = "https://ubuntu-server:8181/${appName}-0.1" 
        logFileName = "/home/samuelc/tmadb/logs/tmadbApp."+((new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss")).format(Calendar.getInstance().getTime()))+".log"
        // Dojo Plugin Properties
        dojo.optimize.during.build = false;
        dojo.use.customBuild.js = false;
    }
}

// session timeout ...
grails.sessionTimeout = 60*60

// add dojo
grails.views.javascript.library="dojo"

// for custom dojo build 
// - make sure that the resource plugin doesn't try to interfere with dojo custom huild
// ref: http://grails.1312388.n4.nabble.com/Dojo-plugin-doing-custom-build-td4638256.html
grails.resources.mappers.hashandcache.excludes = ['**/dojo/**']
grails.resources.adhoc.excludes = ['**/dojo/**']
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*']

// do custom dojo build
// reference: http://grails.1312388.n4.nabble.com/Dojo-plugin-doing-custom-build-td4638256.html
// Dojo Plugin Properties
/**
 * Use for Dojo 1.7 and up...
 * Only Dojo Grails Plugin understand this. It will create a single css file from these
 * files. Each of these files will be @import'ed so that the dojo build process will
 * detect and combine them.
 * 
 * 
 * Sam Leung notes: 2016-05-16: 
 *    adding stagingDir under dojo.profile did NOT work
 *    var stagingDir = "${grailsSettings.projectWarExplodedDir}"; did NOT work either!!!
 * 
 * 2016-05-16: adding def stagingDir = "${grailsSettings.projectWarExplodedDir}";
 * to target/work/plugins/dojo-1.7.2.0/scripts/_DojoTasks.groovy
 * seems to work?!?!
 */
dojo.css = """
  dependencies: [
    "../dojo/resources/dojo.css",
    "../dijit/themes/dijit.css",
    "../dijit/themes/tundra/tundra.css",
    "../dojoui/resources/css/dojo-ui.css",
    "../dojox/grid/resources/Grid.css",
    "../dojox/grid/resources/tundraGrid.css"
  ]
"""

/**
 * Use for Dojo 1.7 and up...
 * New profile file format. See the livedocs for more information:
 * http://livedocs.dojotoolkit.org/build/index
 */
dojo.profile = """
  var profile = {
    releaseDir:"release",
    layerOptimize: "shrinksafe.keepLines",
    cssOptimize: "comments.keepLines",

    packages: [
      {name: "css", location: "css"},
      {name: "dojo", location: "dojo"},
      {name: "dijit", location: "dijit"},
      {name: "dojox", location: "dojox"},
      {name: "dojoui", location: "dojoui"}
    ],

    layers: {
      "dojo/dojo-all": {
        exclude: [ "dojo/_firebug" ],
        include: [

        // Dojo
        "dojo/main",
        "dojo/require",
        "dojo/NodeList-traverse",
        "dojo/dnd/Moveable",
        "dojo/dnd/TimedMoveable",
        "dojo/dnd/AutoSource",
        "dojo/dnd/Target",
        "dojo/io/iframe",
        "dojo/data/ItemFileReadStore",
        "dojo/data/ItemFileWriteStore",
        "dojo/cldr/nls/en/gregorian",
        "dojo/cldr/nls/en/number",
        "dojo/fx/Toggler",

        // Dijit
        "dijit/main",
        "dijit/_base",
        "dijit/_Widget",
        "dijit/Tooltip",
        "dijit/Dialog",
        "dijit/TooltipDialog",
        "dijit/ProgressBar",
        "dijit/Tree",
        "dijit/MenuBar",
        "dijit/Menu",
        "dijit/MenuItem",
        "dijit/PopupMenuBarItem",
        "dijit/PopupMenuItem",
        "dijit/MenuSeparator",
        "dijit/Editor",
        "dijit/_editor/plugins/TextColor",
        "dijit/_editor/plugins/ViewSource",
        "dijit/_editor/plugins/LinkDialog",
        "dijit/_editor/plugins/FontChoice",
        "dijit/layout/TabContainer",
        "dijit/layout/ContentPane",
        "dijit/form/DateTextBox",
        "dijit/form/TimeTextBox",
        "dijit/form/NumberSpinner",
        "dijit/form/ComboButton",
        "dijit/form/MultiSelect",

        // Dojox
        "dojox/main",
        "dojox/grid/DataGrid",
        "dojox/editor/plugins/PasteFromWord",

        //DojoUI
        "dojoui/Bind",
        "dojoui/DojoGrailsSpinner",
        "dojoui/layout/ContentPane",
        "dojoui/layout/TabContainer",
        "dojoui/widget/DropDownButton",
        "dojoui/widget/DropDownButtonLink",
        "dojoui/widget/ForestStoreModel",
        "dojoui/widget/DataSourceView",
        "dojoui/widget/Tree"
      ]}
    }
  };
"""

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}
