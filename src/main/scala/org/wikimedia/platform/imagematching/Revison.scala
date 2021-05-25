package org.wikimedia.platform.imagematching

case class Performer(
                      user_text: String,
                      user_groups: List[String],
                      user_is_bot: Boolean,
                      user_id: Long,
                      user_registration_dt: String,
                      user_edit_count: Long
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
                           performer: Performer,
                           page_is_redirect: Boolean,
                           rev_parent_id: Long,
                           rev_content_changed: Boolean,
                           rev_is_revert: Boolean
                         )