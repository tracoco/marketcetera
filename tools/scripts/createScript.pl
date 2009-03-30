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


# Error checking.

if (@ARGV%3) {
	warn "\n";
	warn "Usage: ",__FILE__," [<root> <script_name> <class>]+\n";
	die "\n";
}

# Configuration.

my($commonArgs)='-Xms384m -Xmx600m';

# Generate scripts.

while (@ARGV) {
	my($root)=shift(@ARGV);
	my($scriptBase)=shift(@ARGV);
	my($class)=shift(@ARGV);

	# Shorthands.

	my($artifact)=($root=~m/[\/\\]([^\/\\]+)$/io);
	my($bin)=$root.'/bin';
	my($logs)=$root.'/logs';

    # Create output directory.

	mkdir($bin) if (!(-e $bin));
	mkdir($logs) if (!(-e $logs));

    # Create the Windows script.

	my($sep)="\r\n";
	my($script)=$bin.'/'.$scriptBase.'.bat';
	open(OUT,'>'.$script);
	binmode(OUT);
	print OUT '@ECHO OFF'.$sep.$sep;

	print OUT 'REM'.$sep;
	print OUT 'REM This startup file is automatically generated by tools/scripts/createScript.pl'.$sep;
	print OUT 'REM'.$sep.$sep;

	print OUT 'CALL "%~dp0..\\..\\setEnv.bat"'.$sep.$sep;

	print OUT 'SET APPLICATION_DIR='.$artifact.$sep.$sep;

	print OUT 'CD %METC_HOME%\\%APPLICATION_DIR%'.$sep.$sep;

	print OUT 'SET THE_CLASSPATH=.\\conf'.$sep;
	print OUT 'FOR /F %%f IN (\'DIR /B /O:N .\\lib\\*.jar\') DO CALL :SETCP .\\lib\\%%f'.$sep.$sep;

	print OUT 'java.exe '.$commonArgs.
		' -Dorg.marketcetera.appDir=%METC_HOME%\\%APPLICATION_DIR%^'.$sep;
	print OUT ' -cp "%THE_CLASSPATH%"^'.$sep;
	print OUT ' '.$class.' %*'.$sep.$sep;

	print OUT 'GOTO END'.$sep.$sep;

	print OUT ':SETCP'.$sep;
	print OUT 'SET THE_CLASSPATH=%THE_CLASSPATH%;%1'.$sep.$sep;

	print OUT ':END'.$sep;
	close(OUT);

    # Create the Unix script.

	my($sep)="\n";
	my($script)=$bin.'/'.$scriptBase.'.sh';
	open(OUT,'>'.$script);
	binmode(OUT);
	print OUT '#!/bin/sh'.$sep.$sep;

	print OUT '##'.$sep;
	print OUT '## This startup file is automatically generated by tools/scripts/createScript.pl'.$sep;
	print OUT '##'.$sep.$sep;

	print OUT '. "$(dirname $0)/../../setEnv.sh"'.$sep.$sep;

	print OUT 'APPLICATION_DIR='.$artifact.$sep.$sep;

	print OUT 'cd ${METC_HOME}/${APPLICATION_DIR}'.$sep.$sep;

	print OUT 'THE_CLASSPATH=./conf'.$sep;
	print OUT 'for file in `ls -1 ./lib/*.jar`'.$sep;
	print OUT 'do'.$sep;
	print OUT '    THE_CLASSPATH=${THE_CLASSPATH}:${file}'.$sep;
	print OUT 'done'.$sep.$sep;

	print OUT 'exec java '.$commonArgs.
		' -Dorg.marketcetera.appDir=${METC_HOME}/${APPLICATION_DIR}\\'.$sep;
	print OUT ' -cp "${THE_CLASSPATH}"\\'.$sep;
	print OUT ' '.$class.' $*'.$sep;
	close(OUT);
	chmod(0755,$script);
}
