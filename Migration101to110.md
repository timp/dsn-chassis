# Introduction #

How to upgrade from Chassis Manta 1.0.1 to 1.1


# Details #
## Add the email configuration to the server context ##
See configuration page for details

## Upgrade the war ##
Deploy the latest war

## Run the following upgrade scripts ##
  1. /repository/service/admin/migrate-draft-1.0.1-to-1.1.xql
  1. /repository/service/admin/migrate-study-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-curatedMedia-feed-config-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-curatedMedia-member-security-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-study-collection-security-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-study-info-collection-security-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-study-info-feed-config-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-study-info-member-security-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-study-member-security-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-submittedMedia-feed-config-1.0.1-to-1.1.xql
  1. /repository/service/admin/update-submittedMedia-member-security-1.0.1-to-1.1.xql