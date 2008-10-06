# Run-time Windows and Unix executable script generator.
#
# Author: tlerios@marketcetera.com
# Since: 0.5.0
# Version: $Id$
# $License$
#
# FUTURE: If messages are an output of the generated scripts, then 
#         those messages will have to be localized using resource
#         bundles, not hard-coded.


use strict;
use Getopt::Std;


# Extract arguments.

use vars qw($opt_j);
getopts('j');

# Error checking.

if (@ARGV!=3) {
  warn "\n";
  warn "Usage: ",__FILE__," [-j(mx)] [-c(onf)] <root> <name> <class>\n";
  die "\n";
}

# Configuration.

my($root)=$ARGV[0];
my($scriptBase)=$ARGV[1];
my($class)=$ARGV[2];
my($artifact)=($root=~m/[\/\\]([^\/\\]+)$/io);

my($lib)=$root.'/lib';
my($bin)=$root.'/bin';
my($logs)=$root.'/logs';
my($commonArgs)='-Xms384m -Xmx600m';
if ($opt_j) {
	$commonArgs.=' -Dcom.sun.management.jmxremote';
}

# Create output directory.

mkdir($bin) if (!(-e $bin));
mkdir($logs) if (!(-e $logs));

# Fetch jar files.

opendir(DIR,$lib);
my(@jars);
my($jar);
while ($jar=readdir(DIR)) {
	next if ($jar!~/\.jar$/io);
	push(@jars,$jar);
}
closedir(DIR);
@jars=sort(@jars);

# Create the Windows script.

my($sep)="\r\n";
my($script)=$bin.'/'.$scriptBase.'.bat';
open(OUT,'>'.$script);
binmode(OUT);
print OUT '@ECHO OFF'.$sep.$sep;
print OUT 'CALL "%~dp0..\\..\\setEnv.bat"'.$sep;
print OUT 'java.exe '.$commonArgs.' -Dorg.marketcetera.appDir="%METC_HOME%\\'.$artifact.'"^'.$sep;
print OUT ' -cp "%METC_HOME%\\'.$artifact.'\\conf"^'.$sep;
foreach $jar (@jars) {
	print OUT ';"%METC_HOME%\\'.$artifact.'\\lib\\'.$jar.'"^'.$sep;
}
print OUT ' '.$class.' %*'.$sep;
close(OUT);

# Create the Unix script.

my($sep)="\n";
my($script)=$bin.'/'.$scriptBase.'.sh';
open(OUT,'>'.$script);
binmode(OUT);
print OUT '#!/bin/sh'.$sep.$sep;
print OUT '. "$(dirname $0)/../../setEnv.sh"'.$sep;
print OUT 'exec java '.$commonArgs.' -Dorg.marketcetera.appDir="${METC_HOME}/'.$artifact.'"\\'.$sep;
print OUT ' -cp "${METC_HOME}/'.$artifact.'/conf"\\'.$sep;
foreach $jar (@jars) {
	print OUT ':"${METC_HOME}/'.$artifact.'/lib/'.$jar.'"\\'.$sep;
}
print OUT ' '.$class.' $*'.$sep;
close(OUT);
chmod(0755,$script);