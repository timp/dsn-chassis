#!/usr/bin/perl -w

use strict;
use warnings;

my $urlName = "questionnaire_1024_";
my $csvFileName = $urlName ."1.csv";
open FILE1, "<$csvFileName" or die $!;
my @lines1 = <FILE1>;

$csvFileName = $urlName ."2.csv";
open FILE2, "<$csvFileName" or die $!;
my @lines2 = <FILE2>;

$csvFileName = $urlName ."3.csv";
open FILE3, "<$csvFileName" or die $!;
my @lines3 = <FILE3>;

$csvFileName = $urlName ."4.csv";
open FILE4, "<$csvFileName" or die $!;
my @lines4 = <FILE4>;

$csvFileName = $urlName ."5.csv";
open FILE5, "<$csvFileName" or die $!;
my @lines5 = <FILE5>;

$csvFileName = $urlName ."6.csv";
open FILE6, "<$csvFileName" or die $!;
my @lines6 = <FILE6>;

$csvFileName = $urlName ."7.csv";
open FILE7, "<$csvFileName" or die $!;
my @lines7 = <FILE7>;

$csvFileName = $urlName ."8.csv";
open FILE8, "<$csvFileName" or die $!;
my @lines8 = <FILE8>;

$csvFileName = $urlName ."9.csv";
open FILE9, "<$csvFileName" or die $!;
my @lines9 = <FILE9>;

$csvFileName = $urlName ."10.csv";
open FILE10, "<$csvFileName" or die $!;
my @lines10 = <FILE10>;

open JOINED, ">questionnaire.csv" or die $!;

for (my $i=0; $i < 101; $i++) {
	my $line = $lines1[$i];
	$line =~ s/\n//;
	
	my $nextFileLine = $lines2[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	my $nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines3[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines4[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines5[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines6[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines7[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines8[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines9[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	$nextFileLine = $lines10[$i];
	$nextFileLine =~ m/^[^,]+,(.+)\n$/;
	$nextCol = $1;
	$line = $line . "," . $nextCol;

	print JOINED "$line\n";
}
