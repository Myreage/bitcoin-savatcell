# common variables
TMPOUTPUTS=tmp
GREP=grep --color=never
U2D=tr -d "\\r"

# default dbms host
DBMSHOST?=localhost
# dbms port to connect to
DBMSPORT?=33000

# delete the test ouput after test ?
DELETE?=no

# stop immediately when one test fails
STOPONERROR?=no

#
# ------------- !!! DO NOT CHANGE ANYTHING AFTER THIS LINE !!! ----------------
#

ifeq ($(STOPONERROR),yes)
CHECK_RET_CMD := if [ -s $(TMPOUTPUTS)/$$t.Test$(PARTSUFFIX).diff ] ; then break ; fi
else
CHECK_RET_CMD := true
endif

CLASS_PATH="jdbc.jar;extra/jssc.jar"

RUNTEST := java -Djdbc.debug=false -Djdbc.port=$(DBMSPORT) -classpath $(CLASS_PATH) test.runner.MainRunner $(TEST) $(DBMSHOST) > $(TMPOUTPUTS)/$(TEST).out
#RUNTEST := java -Djdbc.debug=false -Djdbc.port=$(DBMSPORT) -cp jdbc.jar test.runner.MainRunner $(TEST) $(DBMSHOST) > $(TMPOUTPUTS)/$(TEST).out

.PHONY:			all help test clean testall
.DEFAULT:		all

all: clean
	@mkdir -p classes
	@ant --noconfig -q -f build_inria.xml build
	@(cd classes; jar cf ../jdbc.jar *)

help:
	@echo "Usage:"
	@echo "	make ( all | testall | clean | test TEST=<test name> ) [DBMSHOST=<dbms address>] [DBMSPORT=<port number>] [PAUSE=yes] [DELETE=no]"
	@echo "		DBMSHOST			host name or address of DBMS server (localhost if local, COM4 if using virtual COM port)"
	@echo "		DBMSPORT			port number of DBMS server (defaults to 33000, does not need to be specified if using virtual COM port)"
	@echo "		PAUSE=yes			Wait for key input between each test"
	@echo "		DELETE=no			Do not delete temporary files (in directory tmp/)"
	@echo "		STOPONERROR=no	Stop immediately when one test fails (for make testall)"
	@echo
	@echo "Common targets:"
	@echo "	 use, help				- Print this help"
	@echo "	 clean					- Clean directories classes/ and tmp/"
	@echo "	 all					- Compile everything for PC platform (make sure JAVA_HOME and ANT_HOME are set correctly)"
	@echo "	 testall				- Run all the tests listed in file tests.lst"
	@echo "	 testpds				- Run all the tests listed in file testsPDS.lst"
	@echo "	 test TEST=<test name>	- Run given test (ex: TEST=test.jdbc.INRIA_1.Test)"

use: help

clean:
	@rm -rf $(TMPOUTPUTS) classes

testall:
	@for t in $(shell $(GREP) -v '^#' tests.lst) ; \
	do \
		$(MAKE) --no-print-directory test TEST=$$t.Test ; \
		$(CHECK_RET_CMD) ; \
	done

testpds:
	@for t in $(shell $(GREP) -v '^#' testsPDS.lst) ; \
	do \
		$(MAKE) --no-print-directory test TEST=$$t.Test ; \
		$(CHECK_RET_CMD) ; \
	done

test:
	@mkdir -p $(TMPOUTPUTS)
ifeq ($(TEST),)
	$(error Unspecified test, use: TEST=<test class name>)
else
	@TESTPKG=`echo $(TEST) | sed s+\.Test++` ; \
	TESTOUT=`echo $(TEST) | sed -e "s+\.+/+g" | sed s+Test+output$(PARTSUFFIX).txt+` ; \
	printf "==> $$TESTPKG ..." ; \
	$(RUNTEST) ; \
	if [ "$(PART)" != "1" -o -s $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).out ] ; \
	then \
		cat src/jdbc-tests/$$TESTOUT | $(U2D) > $(TMPOUTPUTS)/out.txt ; \
		cat $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).out | $(U2D) > $(TMPOUTPUTS)/tmp.txt ; \
		if `diff -pruN $(TMPOUTPUTS)/out.txt $(TMPOUTPUTS)/tmp.txt > $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).diff` ; \
		then \
			echo "OK" ; \
		else \
			echo "KO, mismatch:" ; \
			echo "-8<-------------------------------------------" ; \
			cat $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).diff ; \
			echo "------------------------------------------->8-" ; \
		fi ; \
		rm $(TMPOUTPUTS)/out.txt $(TMPOUTPUTS)/tmp.txt ; \
	else \
		echo OK ; \
	fi
ifeq ($(DELETE),yes)
	@rm $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).out $(TMPOUTPUTS)/$(TEST)$(PARTSUFFIX).diff
endif
endif
