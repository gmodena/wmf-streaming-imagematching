package org.wikimedia.platform.imagematching

case class Performer(
                      user_text: Option[String] = None,
                      user_groups: Option[List[String]] = None,
                      user_is_bot: Option[Boolean] = None,
                      user_id: Option[Long] = None,
                      user_registration_dt: Option[String] = None,
                      user_edit_count: Option[Long] = None
                    )

case class RevisionCreate(
                           database: String,
                           page_id: Long,
                           page_title: String,
                           page_namespace: Int,
                           rev_id: Long,
                           rev_timestamp: String,
                           rev_sha1: String,
                           rev_minor_edit: Boolean,
                           rev_len: Int,
                           rev_content_model: String,
                           rev_content_format: String,
                           performer: Option[Performer] = None,
                           page_is_redirect: Boolean,
                           rev_parent_id: Option[Long] = None,
                           rev_content_changed: Option[Boolean] = None,
                           rev_is_revert: Option[Boolean] = None
                         )