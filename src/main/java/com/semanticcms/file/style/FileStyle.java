/*
 * semanticcms-file-style - Default style for files nested within SemanticCMS pages and elements.
 * Copyright (C) 2016, 2017, 2020, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-file-style.
 *
 * semanticcms-file-style is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-file-style is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-file-style.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.semanticcms.file.style;

import com.aoapps.net.Path;
import com.aoapps.web.resources.registry.Group;
import com.aoapps.web.resources.registry.Style;
import com.aoapps.web.resources.servlet.RegistryEE;
import com.semanticcms.core.servlet.SemanticCMS;
import com.semanticcms.file.model.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("Registers the styles for files in RegistryEE and SemanticCMS.")
public class FileStyle implements ServletContextListener {

  public static final Group.Name RESOURCE_GROUP = new Group.Name("semanticcms-file-style");

  // TODO: Change to Group.Name once we have group-level ordering
  public static final Style SEMANTICCMS_FILE = new Style("/semanticcms-file-style/semanticcms-file.css");

  @Override
  public void contextInitialized(ServletContextEvent event) {
    ServletContext servletContext = event.getServletContext();

    // Add our CSS file
    RegistryEE.Application.get(servletContext)
        .activate(RESOURCE_GROUP) // TODO: Activate as-needed
        .getGroup(RESOURCE_GROUP)
        .styles
        .add(SEMANTICCMS_FILE);

    SemanticCMS semanticCMS = SemanticCMS.getInstance(servletContext);
    // Add link CSS classes
    semanticCMS.addLinkCssClassResolver(
        File.class,
        // TODO: Multiple classes based on file type (from extension or mime type/magic?)
        file -> file.getPageRef().getPath().endsWith(Path.SEPARATOR_STRING)
            ? "semanticcms-file-directory-link"
            : "semanticcms-file-file-link"
    );
    // Add list item CSS classes
    semanticCMS.addListItemCssClassResolver(
        File.class,
        // TODO: Multiple classes based on file type (from extension or mime type/magic?)
        file -> file.getPageRef().getPath().endsWith(Path.SEPARATOR_STRING)
            ? "semanticcms-file-list-item-directory"
            : "semanticcms-file-list-item-file"
    );
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    // Do nothing
  }
}
