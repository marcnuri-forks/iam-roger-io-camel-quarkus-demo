#!/bin/bash

mvn  clean package -Pnative

mvn oc:build oc:resource oc:apply -Popenshift
