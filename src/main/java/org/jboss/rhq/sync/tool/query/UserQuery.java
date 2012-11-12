/*
*
* RHQ Sync Tool
* Copyright (C) 2012-2013 Red Hat, Inc.
* All rights reserved.
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License,
* version 2.1, as published by the Free Software Foundation.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License and the GNU Lesser General Public License
* for more details.
*
* You should have received a copy of the GNU General Public License
* and the GNU Lesser General Public License along with this program;
* if not, write to the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*
*/

package org.jboss.rhq.sync.tool.query;

import java.util.Collection;
import java.util.List;

import org.rhq.core.domain.auth.Subject;
import org.rhq.core.domain.authz.Role;

/**
 * Created by IntelliJ IDEA.
 * User: imckinle
 * Date: 11/1/11
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserQuery {
    public List<Subject> getAllUsers();
    public Collection<Subject> addAlllUsers(Collection<Subject> subjects);
	public Collection<Subject> retrievedExistingSubjectsAmong(Collection<Subject> subjects);

    public List<Role> getallRoles();
    public Collection<Role> addAllRoles(Collection<Role> roles);
    public Role addRole(Role role);
	public Collection<Role> findRolesByNames(Role role);
	
	public void addSubjectsToRole(Role role, Collection<Subject> subjects);
}
