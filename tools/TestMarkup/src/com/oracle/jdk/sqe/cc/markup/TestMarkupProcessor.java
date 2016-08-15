/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 */
package com.oracle.jdk.sqe.cc.markup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.tools.Diagnostic;

/**
 *
 * @author shura
 */
@SupportedAnnotationTypes("com.oracle.jdk.sqe.cc.markup.Covers")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedOptions(Covers.COVERAGE_FILE_PROPERTY)
public class TestMarkupProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        CoverageStorage cov = new CoverageStorage(false);
        File coverageFile = null;
        for (TypeElement te : set) {
            try {
                String file = processingEnv.getOptions().get(Covers.COVERAGE_FILE_PROPERTY);
                if (file == null || file.length() == 0) {
                    file = System.getProperty("user.dir") + File.separator + "coverage.fcov";
                }
                coverageFile = new File(file);
                if (coverageFile.exists()) {
                    cov.read(coverageFile);
                }
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Going to write into " + coverageFile.getAbsolutePath());
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
            for (Element el : re.getElementsAnnotatedWith(te)) {
                switch (el.getKind()) {
                    case CLASS:
                    case INTERFACE:
                        addToCoverage(cov,
                                ((TypeElement) el).getQualifiedName().toString(), el.getAnnotation(Covers.class));
                        break;
                    case METHOD:
                        TypeElement cls = (TypeElement) el.getEnclosingElement();
                        ExecutableElement mthd = (ExecutableElement) el;
                        String qualifiedName = cls.getQualifiedName() + "."
                                + mthd.getSimpleName() + "(";
                        List<? extends TypeParameterElement> params =
                                mthd.getTypeParameters();
                        for (int i = 0; i < params.size(); i++) {
                            qualifiedName += params.get(i).getBounds().get(0).toString();
                            if (i < params.size() - 1) {
                                qualifiedName += ",";
                            }
                        }
                        qualifiedName += ")";
                        addToCoverage(cov,
                                qualifiedName, el.getAnnotation(Covers.class));
                        break;
                }
            }
            try {
                cov.write(coverageFile);
            } catch (IOException ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getMessage());
            }
        }
        return true;
    }

    private void addToCoverage(CoverageStorage cov, String qualifiedName, Covers annotation) {
        if (annotation != null) {
            for (String feature : annotation.value()) {
                cov.add(feature, qualifiedName, annotation.level());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "now " + cov.coverage.keySet().size());
            }
        }
    }
}
