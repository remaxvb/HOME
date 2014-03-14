require 'faye'

bayeux = Faye::RackAdapter.new(:mount => '/bayeux', :timeout => 25)
run bayeux

def bayeux.log(message)
end

bayeux.bind(:subscribe) do |client_id, channel|
  puts "[  SUBSCRIBE] #{client_id} -> #{channel}"
end

bayeux.bind(:unsubscribe) do |client_id, channel|
  puts "[UNSUBSCRIBE] #{client_id} -> #{channel}"
end

bayeux.bind(:disconnect) do |client_id|
  puts "[ DISCONNECT] #{client_id}"
end
