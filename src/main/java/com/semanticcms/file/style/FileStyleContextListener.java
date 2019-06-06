/*
 * semanticcms-file-style - Default style for files nested within SemanticCMS pages and elements.
 * Copyright (C) 2016, 2017  AO Industries, Inc.
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
 * along with semanticcms-file-style.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.file.style;

import com.aoindustries.net.Path;
import com.semanticcms.core.servlet.SemanticCMS;
import com.semanticcms.file.model.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("Registers the styles for files in SemanticCMS.")
public class FileStyleContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		SemanticCMS semanticCMS = SemanticCMS.getInstance(event.getServletContext());
		// Add our CSS file
		semanticCMS.addCssLink("/semanticcms-file-style/styles.css");
		// Add link CSS classes
		semanticCMS.addLinkCssClassResolver(
			File.class,
			new SemanticCMS.LinkCssClassResolver<File>() {
				@Override
				public String getCssLinkClass(File file) {
					// TODO: Multiple classes based on file type (from extension or mime type/magic?)
					if(file.getPageRef().getPath().endsWith(Path.SEPARATOR_STRING)) {
						return "semanticcms-file-directory-link";
					} else {
						return "semanticcms-file-file-link";
					}
				}
			}
		);
		// Add list item CSS classes
		semanticCMS.addListItemCssClassResolver(
			File.class,
			new SemanticCMS.ListItemCssClassResolver<File>() {
				@Override
				public String getListItemCssClass(File file) {
					// TODO: Multiple classes based on file type (from extension or mime type/magic?)
					if(file.getPageRef().getPath().endsWith(Path.SEPARATOR_STRING)) {
						return "semanticcms-file-list-item-directory";
					} else {
						return "semanticcms-file-list-item-file";
					}
				}
			}
		);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Do nothing
	}
}
