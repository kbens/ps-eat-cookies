# ps-eat-cookies
PeopleSoft servlet filter that prevents unwanted Cookies, like PS_TOKEN.

# Setup
1. Place the sdk directory in PS_CUST_HOME.
2. Place the classes directory in PS_CFG_HOME. `%PS_CFG_HOME%/webserv/[site]/applications/[domain]/PORTAL.war/WEB-INF/`
3. Copy the text from template-web.xml and place in the web.xml, change as needed. `%PS_CFG_HOME%/webserv/[site]/applications/[domain]/PORTAL.war/WEB-INF/web.xml`
4. Bounce the web server.
 
# Parameters
Set these parameters with the filter setup in the web.xml file.
## logFence
- 0 - minimal details
- 1 - all details

## cookiesToEat
- Comma delimited list of unwanted Cookies. `PS_TOKEN,PS_TOKENEXPIRE`
