
while (<>) {
    $oldStudy = $currentStudy;
    ($id, $name, $date) = split(/#/,$_);
    @p = split(/\//,$id);
    $currentStudy = $p[8];
    if ($oldStudy ne $currentStudy) {
        
        foreach $key (keys(%filenames)) {
            print $filenames{$key};
            #Not sure why this is necessary but = () doesn't seem to work...
            $filenames{$key}='';
            $filetimes{$key}='';
        }
        @filenames = ();
        @filetimes = ();
    }
    if ($filenames{$name} ne '') {
        ($d1, $time) = split(/T/,$date);
        ($year, $month, $day) = split(/-/,$d1);
        ($hour, $min, $rest) = split(/:/, $time);
        ($sec) = split(/\./, $rest);
        
        ($pd1, $ptime) = split(/T/,$filetimes{$name});
        ($pyear, $pmonth, $pday) = split(/-/,$pd1);
        ($phour, $pmin, $prest) = split(/:/, $ptime);
        ($psec) = split(/\./, $prest);
        $polder = 0;
        
        if ($pyear > $year || 
            ($pyear == $year && $pmonth > $month) || 
            ($pyear == $year && $pmonth == $month && $pday > $day)) {
     #       print "polder year $date $year $month $day $filetimes{$name} $pyear $pmonth $pday\n";
            $polder = 1;
        } elsif ($pyear == $year && $pmonth == $month && $pday == $day) {
            if ($phour > $hour || 
            ($phour == $hour && $pmin > $min) || 
            ($phour == $hour && $pmin == $min && $psec > $sec)) {
    #            print "polder older $date $filetimes{$name} \n";
                $polder = 1;
            }
        } else {
   #            print "p not older $date $filetimes{$name} \n";
        }
        if ($polder == 0) {
  #          print "polder == false\n";
            $filenames{$name} = $_;
            $filetimes{$name} = $date;
        }
        
    } else {
        $filenames{$name} = $_;
        $filetimes{$name} = $date;
    }
}
foreach $key (keys(%filenames)) {
    print $filenames{$key};
}