/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.rubypeople.rdt.internal.core.search.matching;

import org.eclipse.core.resources.IResource;
import org.jruby.ast.Node;
import org.rubypeople.rdt.core.search.SearchDocument;
import org.rubypeople.rdt.internal.core.Openable;
import org.rubypeople.rdt.internal.core.RubyScript;
import org.rubypeople.rdt.internal.core.util.CharOperation;
import org.rubypeople.rdt.internal.core.util.Util;

public class PossibleMatch {

public static final String NO_SOURCE_FILE_NAME = "NO SOURCE FILE NAME"; //$NON-NLS-1$

public IResource resource;
public Openable openable;
public MatchingNodeSet nodeSet;
public char[][] compoundName;
Node parsedUnit;
public SearchDocument document;
private String sourceFileName;
private char[] source;

public PossibleMatch(MatchLocator locator, IResource resource, Openable openable, SearchDocument document, boolean mustResolve) {
	this.resource = resource;
	this.openable = openable;
	this.document = document;
	this.nodeSet = new MatchingNodeSet(mustResolve);
	char[] qualifiedName = getQualifiedName();
	if (qualifiedName != null)
		this.compoundName = CharOperation.splitOn('.', qualifiedName);
}
public void cleanUp() {
	this.source = null;
	if (this.parsedUnit != null) {
//		this.parsedUnit.cleanUp();
		this.parsedUnit = null;
	}
	this.nodeSet = null;
}
public boolean equals(Object obj) {
	if (this.compoundName == null) return super.equals(obj);
	if (!(obj instanceof PossibleMatch)) return false;

	// By using the compoundName of the source file, multiple .class files (A, A$M...) are considered equal
	// Even .class files for secondary types and their nested types
	return CharOperation.equals(this.compoundName, ((PossibleMatch) obj).compoundName);
}
public char[] getContents() {
	if (this.source != null) return this.source;
	return this.source = this.document.getCharContents();
}
/**
 * The exact openable file name. In particular, will be the originating .class file for binary openable with attached
 * source.
 * @see org.eclipse.jdt.internal.compiler.env.IDependent#getFileName()
 * @see PackageReferenceLocator#isDeclaringPackageFragment(IPackageFragment, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)
 */
public char[] getFileName() {
	return this.openable.getElementName().toCharArray();
}
public char[] getMainTypeName() {
	// The file is no longer opened to get its name => remove fix for bug 32182
	return this.compoundName[this.compoundName.length-1];
}
public char[][] getPackageName() {
	int length = this.compoundName.length;
	if (length <= 1) return CharOperation.NO_CHAR_CHAR;
	return CharOperation.subarray(this.compoundName, 0, length - 1);
}
/*
 * Returns the fully qualified name of the main type of the compilation unit
 * or the main type of the .rb file
 */
private char[] getQualifiedName() {
	if (this.openable instanceof RubyScript) {
		// get file name
		String fileName = this.openable.getElementName(); // working copy on a .class file may not have a resource, so use the element name
		// get main type name
		char[] mainTypeName = Util.getNameWithoutRubyLikeExtension(fileName).toCharArray();
		RubyScript cu = (RubyScript) this.openable;
		return cu.getType(new String(mainTypeName)).getFullyQualifiedName().toCharArray();
	}
	return null;
}
/*
 * Returns the source file name of the class file.
 * Returns NO_SOURCE_FILE_NAME if not found.
 */
private String getSourceFileName() {
	if (this.sourceFileName != null) return this.sourceFileName;

	this.sourceFileName = NO_SOURCE_FILE_NAME; 
	return this.sourceFileName;
}	
public int hashCode() {
	if (this.compoundName == null) return super.hashCode();

	int hashCode = 0;
	for (int i = 0, length = this.compoundName.length; i < length; i++)
		hashCode += CharOperation.hashCode(this.compoundName[i]);
	return hashCode;
}
public String toString() {
	return this.openable == null ? "Fake PossibleMatch" : this.openable.toString(); //$NON-NLS-1$
}
}
