#!/bin/bash
curl -s "http://192.168.188.1:49000/igdupnp/control/WANIPConn1" -H "Content-Type: text/xml; charset="utf-8"" -H "SoapAction:urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination" -d "@reconnect.xml" >/dev/null
#curl -s    "http://192.168.188.1:49000/upnp/control/WANIPConn1" -H "Content-Type: text/xml; charset="utf-8"" -H "SoapAction:urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination" -d "@reconnect.xml" >/dev/null
#curl -s "http://192.168.188.1:49000/igdupnp/control/WANIPConn1" -H "Content-Type: text/xml; charset="utf-8"" -H "SoapAction:urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination" -d "@reconnect.xml" >/dev/null
