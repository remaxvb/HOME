# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20140314105943) do

  create_table "invitations", force: true do |t|
    t.integer  "user_id",    null: false
    t.integer  "member_id",  null: false
    t.integer  "type",       null: false
    t.integer  "status"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "invitations", ["member_id"], name: "member_id", using: :btree
  add_index "invitations", ["user_id"], name: "user_id", using: :btree

  create_table "members", force: true do |t|
    t.integer "user_id",    null: false
    t.integer "tour_id",    null: false
    t.integer "group_id"
    t.integer "status"
    t.float   "longtitude"
    t.float   "lattitude"
  end

  add_index "members", ["tour_id"], name: "tour_id", using: :btree
  add_index "members", ["user_id", "tour_id"], name: "index_members_on_user_id_and_tour_id", unique: true, using: :btree

  create_table "tours", force: true do |t|
    t.string   "title",         limit: 100
    t.string   "destination",   limit: 100
    t.float    "adult_price"
    t.float    "child_price"
    t.date     "depature_date"
    t.date     "return_date"
    t.integer  "id_manager",                null: false
    t.integer  "max_members"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "description"
    t.string   "currency"
  end

  create_table "users", force: true do |t|
    t.string   "email",                          default: "", null: false
    t.string   "encrypted_password",             default: "", null: false
    t.string   "firstname",           limit: 50
    t.string   "lastname",            limit: 50
    t.string   "address",             limit: 50
    t.string   "phone",               limit: 15
    t.date     "date_of_birth"
    t.string   "avatar_file_name"
    t.string   "avatar_content_type"
    t.integer  "avatar_file_size"
    t.datetime "avatar_updated_at"
    t.boolean  "gender"
    t.string   "login_token"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "users", ["email"], name: "index_users_on_email", unique: true, using: :btree

end
