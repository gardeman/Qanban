/*
 * Copyright 2009 Qbranch AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.qbranch.qanban

class UserEventCreate extends UserEvent {

  static constraints = {
    username(blank: false, unique: true)
    userRealName(blank: false)
    email(nullable: false, blank: false, email:true)
    enabled( nullable: true)
    emailShow( nullable: true)
    description( nullable: true, blank: true )
    passwd( nullable: true, blank: false )
  }

  static transients = ['list']

  String username
  String userRealName
  String email
  boolean enabled = true
  boolean emailShow = true
  String description = ''
  String passwd

  public List getItems() {
    return [dateCreated, user]
  }

  def beforeInsert = {
    generateDomainId(username,userRealName,email)
    userDomainId = domainId // You create yourself
  }

  def process(){
    user = new User()
    user.username = username
    user.userRealName = userRealName
    user.email = email
    user.domainId = domainId
    user.enabled = enabled
    user.emailShow = emailShow
    user.passwd = passwd
    user.save()
  }

}