
exec { "apt-get update":
  path => "/usr/bin",
}

package { "vim":
  ensure  => present,
  require => Exec["apt-get update"],
}

package { "zip":
  ensure => "installed",
  require => Exec["apt-get update"],
}

package { "nginx":
  ensure => "installed",
  require => Exec["apt-get update"],
}

package { "curl":
  ensure => "installed",
  require => Exec["apt-get update"],
}

package { "openjdk-7-jre-headless":
  ensure => "installed",
  require => Exec["apt-get update"],
}
