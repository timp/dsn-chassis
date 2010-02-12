cd /var/lib/hudson
# Add any new conf files, jobs, users, and content.
svn add -q --parents *.xml jobs/*/config.xml users/*/config.xml userContent/*
# Ignore things in the root we don't care about.
echo -e "war\nlog\n*.log\n*.tmp\n*.old\n*.bak\n*.jar\n*.json" > myignores
svn propset svn:ignore -F myignores . && rm myignores
# Ignore things in jobs/* we don't care about.
echo -e "builds\nlast*\nnext*\n*.txt\n*.log\nworkspace*\ncobertura\njavadoc\nhtmlreports\nncover\ndoclinks" > myignores
svn propset svn:ignore -F myignores jobs/* && rm myignores
# Remove anything from SVN that no longer exists in Hudson.
svn status | grep '\!' | awk '{print $2;}' | xargs -r svn rm
# And finally, check in of course, showing status before and after for logging.
svn st && svn ci --non-interactive --username=mrhudson -m "automated commit of Hudson configuration" && svn st
